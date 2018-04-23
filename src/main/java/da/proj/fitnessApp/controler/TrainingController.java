package da.proj.fitnessApp.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.services.TrainingService;

@RestController
@RequestMapping("/training-day")
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> index(@RequestBody List<Exercise> exercises) {

		String responseText;

		responseText = this.trainingService.createExercises(exercises);

		return new ResponseEntity<String>(responseText, HttpStatus.OK);
	}
	
}
