package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingService {
	public String createTrainingDay(TrainingDay trainingDay, String username);
}
