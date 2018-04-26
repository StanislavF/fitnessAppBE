package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingService {
	public String createTrainingDay(TrainingDay trainingDay, String username);
	
	public List<TrainingDay> getAllTrainingDaysForUser(String date, String user);
	
	public Long deleteTrainingDay(Long trainingDayId);
}
