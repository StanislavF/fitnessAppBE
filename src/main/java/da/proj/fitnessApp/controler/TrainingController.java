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

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.TrainingDay;
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
			@RequestParam("trainerUsername") String trainerUsername, @RequestParam("username") String clientUsername) {

		String responseText = this.trainingService.createTrainingDay(trainingDay, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<List<TrainingDay>> getTrainingDays(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		if (this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
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

		if (this.userService.isTrainerAuthorised(trainerUsername, clientUsername)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		Long deletedTdId = this.trainingService.deleteTrainingDay(trainingDayId);

		return deletedTdId != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<String> updateTrainingDay(@RequestBody TrainingDay newTrainingDay,
			@RequestParam("oldTrainingDayId") Long oldTrainingDayId,
			@RequestParam("trainerUsername") String trainerUsername, @RequestParam("username") String clientUsername) {

		String responseText = this.trainingService.updateTrainingDay(newTrainingDay, oldTrainingDayId, clientUsername, trainerUsername);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

}
