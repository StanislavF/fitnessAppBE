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
			@RequestParam("trainerUsername") String trainerUsername, @RequestParam("username") String clientUsername) {

		String responseText;

		responseText = this.mealService.createSingleMeal(singleMeal, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<List<SingleMeal>> getSingleMeals(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		if (this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
			return new ResponseEntity<List<SingleMeal>>(HttpStatus.UNAUTHORIZED);
		}

		List<SingleMeal> response;

		response = this.mealService.getAllSingleMealsForUser(date, clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<List<SingleMeal>>(response, HttpStatus.OK)
				: new ResponseEntity<List<SingleMeal>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteSingleMeal(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("SingleMealId") Long SingleMealId) {

		if (this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Long deletedTdId;

		deletedTdId = this.mealService.deleteSingleMeal(SingleMealId);

		return deletedTdId != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
