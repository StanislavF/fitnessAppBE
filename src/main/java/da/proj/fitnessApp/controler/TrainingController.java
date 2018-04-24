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

@RestController
@RequestMapping("/training-day")
public class TrainingController {

	@Autowired
	private TrainingService trainingService;

	@RequestMapping(value = "/createTD", method = RequestMethod.POST)
	public ResponseEntity<String> createTrainingDay(@RequestBody TrainingDay trainingDay,
			@RequestParam("username") String username) {

		String responseText;

		responseText = this.trainingService.createTrainingDay(trainingDay, username);

		return new ResponseEntity<String>(responseText, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTDs", method = RequestMethod.POST)
	public ResponseEntity<String> getTrainingDays(@RequestParam("trainerUsername") String trainerUsername,
			@RequestParam("clientUsername") String clientUsername, @RequestParam("date") String date) {

		String responseText;

		responseText = this.trainingService.createTrainingDay(trainingDay, username);

		return new ResponseEntity<String>(responseText, HttpStatus.OK);
	}

}
