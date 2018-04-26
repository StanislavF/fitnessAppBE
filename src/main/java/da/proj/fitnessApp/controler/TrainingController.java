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

	@RequestMapping(value = "/createTD", method = RequestMethod.POST)
	public ResponseEntity<String> createTrainingDay(@RequestBody TrainingDay trainingDay,
			@RequestParam("username") String username) {
		

		String responseText;

		responseText = this.trainingService.createTrainingDay(trainingDay, username);

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getTDs", method = RequestMethod.GET)
	public ResponseEntity<List<TrainingDay>> getTrainingDays(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		List<TrainingDay> response;

		response = this.trainingService.getAllTrainingDaysForUser(date, clientUsername);

		return response != null ? new ResponseEntity<List<TrainingDay>>(response, HttpStatus.OK)
				: new ResponseEntity<List<TrainingDay>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/deleteTD", method = RequestMethod.GET)
	public ResponseEntity deleteTrainingDay(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("trainingDayId") Long trainingDayId) {

		Long deletedTdId;

		deletedTdId = this.trainingService.deleteTrainingDay(trainingDayId);

		return deletedTdId != null ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
