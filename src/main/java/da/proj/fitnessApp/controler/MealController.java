package da.proj.fitnessApp.controler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.Food;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.models.enums.RequestStatusEnum;
import da.proj.fitnessApp.services.MealService;
import da.proj.fitnessApp.services.UserService;

@RestController
@RequestMapping("/single-meal")
public class MealController {

	@Autowired
	private MealService mealService;

	@Autowired
	private UserService userService;
	
	@Value("${week.date.format}")
	private String weekDateFormat;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createSingleMeal(@RequestBody SingleMeal singleMeal,
			@RequestParam String trainerUsername, @RequestParam String clientUsername) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse  = this.userService.createUnauthorizedTrainerResponse(authResponse);
		
		if(errorResponse!=null) {
			return errorResponse;
		}
		
		String responseText = this.mealService.createSingleMeal(singleMeal, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<List<SingleMeal>> getSingleMeals(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		if (authResponse == null || authResponse.equals(RequestStatusEnum.REQUESTED.getValue())
				|| authResponse.equals(RequestStatusEnum.REJECTED.getValue())) {
			return new ResponseEntity<List<SingleMeal>>(HttpStatus.UNAUTHORIZED);
		}

		List<SingleMeal> response = this.mealService.getAllSingleMealsForUser(date, clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<List<SingleMeal>>(response, HttpStatus.OK)
				: new ResponseEntity<List<SingleMeal>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSingleMeal(@RequestParam String trainerUsername,
			@RequestParam String clientUsername, @RequestParam Long singleMealId) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse  = this.userService.createUnauthorizedTrainerResponse(authResponse);
		
		if(errorResponse!=null) {
			return errorResponse;
		}

		boolean isDeleted = this.mealService.deleteSingleMeal(singleMealId);

		return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateSingleMeal(@RequestBody SingleMeal newSingleMeal,
			@RequestParam("oldSingleMealId") Long oldSingleMealId,
			@RequestParam("clientUsername") String clientUsername,
			@RequestParam("trainerUsername") String trainerUsername) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse  = this.userService.createUnauthorizedTrainerResponse(authResponse);
		
		if(errorResponse!=null) {
			return errorResponse;
		}

		String responseText = this.mealService.updateSingleMeal(newSingleMeal, oldSingleMealId, clientUsername,
				trainerUsername);

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
	
	@RequestMapping(value = "/foods/get", method = RequestMethod.GET)
	public ResponseEntity<List<Food>> getAllFoods() {

		// ToDo check if trainingDay is of the user
		List<Food> responseText = this.mealService.getAllFoods();

		return responseText != null ? new ResponseEntity<List<Food>>(responseText, HttpStatus.OK)
				: new ResponseEntity<List<Food>>(responseText, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/copy-prev-week", method = RequestMethod.POST)
	public ResponseEntity<List<SingleMeal>> copyPrevWeekSM(@RequestParam String trainerUsername,
			@RequestParam String clientUsername, @RequestParam String date) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse  = this.userService.createUnauthorizedTrainerResponse(authResponse);
		
		if(errorResponse!=null) {
			return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat(this.weekDateFormat);
		Date cuurentWeek = null;
		try {
			cuurentWeek = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(cuurentWeek);
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		Date prevWeek = null;
		prevWeek = cal.getTime();
		
		String previousDateString = sdf.format(prevWeek);

		List<SingleMeal> response = this.mealService.copySingleMeals(previousDateString, date, clientUsername, trainerUsername);

		return response != null ?  new ResponseEntity<List<SingleMeal>>(response, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
