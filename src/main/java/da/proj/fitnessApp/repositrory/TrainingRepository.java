package da.proj.fitnessApp.repositrory;

import java.sql.SQLException;
import java.util.List;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;

public interface TrainingRepository {

	void createExercises(List<Exercise> exercise);

	public void createExerciseRow(List<ExerciseRow> exerciseRows, Long td_id);

	public Long createTrainingDay(TrainingDay trainingDay, String username);

	public List<Exercise> readAllExercises();
	
	public List<TrainingDay> readTrainingDays(String date, String user);
	
	public List<ExerciseRow> readExerciseRowsForTD(Long trainingDayId);
	
	public Long deleteTrainingDay(Long trainingDayId);
}
