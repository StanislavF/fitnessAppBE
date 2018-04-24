package da.proj.fitnessApp.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;
import da.proj.fitnessApp.repositrory.TrainingRepository;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	@Autowired
	private TrainingRepository trainingRepository;
	
	public String createTrainingDay(TrainingDay trainingDay, String username) {
		
		List<Exercise> exercisesToCheck = new ArrayList<>();
		for(ExerciseRow exerciseRow : trainingDay.getExercseRows()) {
			exercisesToCheck.add(exerciseRow.getExercise());
		}
		List<Exercise> newExercises = this.trainingRepository.getMissingExercises(exercisesToCheck);
		this.trainingRepository.createExercises(newExercises);
		
		Long trainingDayId = this.trainingRepository.createTrainingDay(trainingDay, username);
		
		this.trainingRepository.createExerciseRow(trainingDay.getExercseRows(), trainingDayId);
		
		return "CREATED";
		
	}
}
