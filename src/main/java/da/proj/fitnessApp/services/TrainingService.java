package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingService {
	String createTrainingDay(TrainingDay trainingDay, String clientUsername, String trainerUsername);
	
	public List<TrainingDay> getAllTrainingDaysForUser(String date, String clientUsername, String trainerUsername);
	
	public Long deleteTrainingDay(Long trainingDayId);
}
