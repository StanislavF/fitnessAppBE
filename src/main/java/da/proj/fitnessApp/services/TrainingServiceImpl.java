package da.proj.fitnessApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.repositrory.TrainingRepository;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	public String createExercises(List<Exercise> exercises) {
		
		this.trainingRepository.createExercises(exercises);
		
		return "CREATED";
		
	}
}
