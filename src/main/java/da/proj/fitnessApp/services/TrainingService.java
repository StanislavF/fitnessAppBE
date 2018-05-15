package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingService {
	String createTrainingDay(TrainingDay trainingDay, String clientUsername, String trainerUsername);
	
	public List<TrainingDay> getAllTrainingDaysForUser(String date, String clientUsername, String trainerUsername);
	
	public boolean deleteTrainingDay(Long trainingDayId);
	
	String updateTrainingDay(TrainingDay newTrainingDay, Long oldTrainingDayId, String clientUsername, String trainerUsername);
	
	String createComment(Long exerciseRowId, String comment);
	
	List<Exercise> getAllExercises();
}
