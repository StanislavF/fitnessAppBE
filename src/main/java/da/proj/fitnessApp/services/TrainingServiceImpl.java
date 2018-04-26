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

	@Transactional
	public String createTrainingDay(TrainingDay trainingDay, String username) {
		
		if(trainingDay == null || username == null || username.isEmpty()) {
			return null;
		}

		List<Exercise> exercisesToCheck = new ArrayList<>();
		for (ExerciseRow exerciseRow : trainingDay.getExercseRows()) {
			exercisesToCheck.add(exerciseRow.getExercise());
		}

		List<Exercise> allExercises = this.trainingRepository.readAllExercises();

		List<Exercise> missingExercises = this.findMissingExercises(exercisesToCheck, allExercises);

		this.trainingRepository.createExercises(missingExercises);

		Long trainingDayId = this.trainingRepository.createTrainingDay(trainingDay, username);

		this.trainingRepository.createExerciseRow(trainingDay.getExercseRows(), trainingDayId);

		return "CREATED";

	}

	private List<Exercise> findMissingExercises(List<Exercise> currentExercises, List<Exercise> allExercises) {

		List<Exercise> missingExercises = new ArrayList<>();

		for (Exercise exercise : currentExercises) {
			if (allExercises.indexOf(exercise) == -1) {
				missingExercises.add(exercise);
			}
		}

		return missingExercises;
	}
	
	@Transactional
	public List<TrainingDay> getAllTrainingDaysForUser(String date, String user){
		
		if(date == null || date.isEmpty() || user == null || user.isEmpty()) {
			return null;
		}
		
		List<TrainingDay> trainingDays = this.trainingRepository.readTrainingDays(date, user);
		
		for(TrainingDay trainingDay : trainingDays) {
			trainingDay.setExercseRows(this.trainingRepository.readExerciseRowsForTD(trainingDay.getId()));
		}
		
		return trainingDays;
	}
	
	public Long deleteTrainingDay(Long trainingDayId) {
		
		return trainingDayId != null ? this.trainingRepository.deleteTrainingDay(trainingDayId) : null;
		
	}
}
