package da.proj.fitnessApp.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.services.MealService;
import da.proj.fitnessApp.services.UserService;

@RestController
@RequestMapping("/single-meal")
public class MealController {

	@Autowired
	private MealService mealService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createSingleMeal(@RequestBody SingleMeal singleMeal,
			@RequestParam String trainerUsername, @RequestParam String clientUsername) {

		String responseText = this.mealService.createSingleMeal(singleMeal, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<List<SingleMeal>> getSingleMeals(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		if (!this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
			return new ResponseEntity<List<SingleMeal>>(HttpStatus.UNAUTHORIZED);
		}

		List<SingleMeal> response = this.mealService.getAllSingleMealsForUser(date, clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<List<SingleMeal>>(response, HttpStatus.OK)
				: new ResponseEntity<List<SingleMeal>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSingleMeal(@RequestParam String trainerUsername,
			@RequestParam String clientUsername, @RequestParam Long singleMealId) {

		if (!this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		boolean isDeleted = this.mealService.deleteSingleMeal(singleMealId);

		return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSingleMeal(@RequestBody SingleMeal newSingleMeal,
			@RequestParam("oldSingleMealId") Long oldSingleMealId,
			@RequestParam("clientUsername") String clientUsername,
			@RequestParam("trainerUsername") String trainerUsername) {

		String responseText = this.mealService.updateSingleMeal(newSingleMeal, oldSingleMealId, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/comment/create", method = RequestMethod.PUT)
	public ResponseEntity<String> createComment(@RequestParam String clientUsername, @RequestParam Long foodRowId,
			@RequestParam String comment) {

		// ToDo check if trainingDay is of the user
		String responseText = this.mealService.createComment(foodRowId, comment);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

}
