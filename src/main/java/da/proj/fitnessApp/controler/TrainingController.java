package da.proj.fitnessApp.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.models.TrainingDay;
import da.proj.fitnessApp.models.enums.RequestStatusEnum;
import da.proj.fitnessApp.services.TrainingService;
import da.proj.fitnessApp.services.UserService;

@RestController
@RequestMapping("/training-day")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> createTrainingDay(@RequestBody TrainingDay trainingDay,
			@RequestParam String trainerUsername, @RequestParam String clientUsername) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse = this.userService.createUnauthorizedTrainerResponse(authResponse);

		if (errorResponse != null) {
			return errorResponse;
		}

		String responseText = this.trainingService.createTrainingDay(trainingDay, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<List<TrainingDay>> getTrainingDays(@RequestParam String trainerUsername,
			@RequestParam String clientUsername, @RequestParam String date) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);
		
		if (authResponse == null || authResponse.equals(RequestStatusEnum.REQUESTED.getValue())
				|| authResponse.equals(RequestStatusEnum.REJECTED.getValue())) {
			return new ResponseEntity<List<TrainingDay>>(HttpStatus.UNAUTHORIZED);
		}

		List<TrainingDay> response = this.trainingService.getAllTrainingDaysForUser(date, clientUsername,
				trainerUsername);

		return response != null ? new ResponseEntity<List<TrainingDay>>(response, HttpStatus.OK)
				: new ResponseEntity<List<TrainingDay>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTrainingDay(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("trainingDayId") Long trainingDayId) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse = this.userService.createUnauthorizedTrainerResponse(authResponse);

		if (errorResponse != null) {
			return errorResponse;
		}

		boolean isDeleted = this.trainingService.deleteTrainingDay(trainingDayId);

		return isDeleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTrainingDay(@RequestBody TrainingDay newTrainingDay,
			@RequestParam Long oldTrainingDayId, @RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String authResponse = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		ResponseEntity<String> errorResponse = this.userService.createUnauthorizedTrainerResponse(authResponse);

		if (errorResponse != null) {
			return errorResponse;
		}

		String responseText = this.trainingService.updateTrainingDay(newTrainingDay, oldTrainingDayId, clientUsername,
				trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/comment/create", method = RequestMethod.PUT)
	public ResponseEntity<String> createComment(@RequestParam String clientUsername, @RequestParam Long exerciseRowId,
			@RequestParam String comment) {

		// ToDo check if trainingDay is of the user
		String responseText = this.trainingService.createComment(exerciseRowId, comment);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/exercises/get", method = RequestMethod.GET)
	public ResponseEntity<List<Exercise>> getAllExercises() {

		// ToDo check if trainingDay is of the user
		List<Exercise> responseText = this.trainingService.getAllExercises();

		return responseText != null ? new ResponseEntity<List<Exercise>>(responseText, HttpStatus.OK)
				: new ResponseEntity<List<Exercise>>(responseText, HttpStatus.BAD_REQUEST);
	}

}
