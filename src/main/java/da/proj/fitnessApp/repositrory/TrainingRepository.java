package da.proj.fitnessApp.repositrory;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingRepository {

	void createExercises(List<Exercise> exercise);
	Long createExerciseRow(ExerciseRow exerciseRow, Long td_id);
	Long createTrainingDay(TrainingDay trainingDay, Long clientId);
	
}
