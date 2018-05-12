package da.proj.fitnessApp.repositrory;

import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingRepository {

	void createExercises(List<Exercise> exercise);

	void createExerciseRow(List<ExerciseRow> exerciseRows, Long td_id);

	Long createTrainingDay(TrainingDay trainingDay, Long clientId, Long trainerId);

	List<Exercise> readAllExercises();
	
	List<TrainingDay> readTrainingDays(String date, Long clientId, Long trainerId);
	
	List<ExerciseRow> readExerciseRowsForTD(Long trainingDayId);
	
	void deleteTrainingDay(Long trainingDayId);
	
	void createComment(Long exerciseRowId, String comment);
}
