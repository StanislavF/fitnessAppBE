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
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.repositrory.TrainingRepository;
import da.proj.fitnessApp.repositrory.UserRepository;

@Service
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	private TrainingRepository trainingRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String createTrainingDay(TrainingDay trainingDay, String clientUsername, String trainerUsername) {

		if (trainingDay == null || clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}

		this.safeMissingExercises(trainingDay.getExerciseRows());

		User trainer = this.userRepository.readUserByUsername(trainerUsername);
		User client = this.userRepository.readUserByUsername(clientUsername);

		Long trainingDayId = this.trainingRepository.createTrainingDay(trainingDay, client.getId(), trainer.getId());

		this.trainingRepository.createExerciseRow(trainingDay.getExerciseRows(), trainingDayId);

		return "CREATED";

	}

	@Transactional
	public List<TrainingDay> getAllTrainingDaysForUser(String date, String clientUsername, String trainerUsername) {

		if (date == null || date.isEmpty() || clientUsername == null || clientUsername.isEmpty()
				|| trainerUsername == null || trainerUsername.isEmpty()) {
			return null;
		}

		User client = this.userRepository.readUserByUsername(clientUsername);
		User trainer = this.userRepository.readUserByUsername(trainerUsername);

		List<TrainingDay> trainingDays = this.trainingRepository.readTrainingDays(date, client.getId(),
				trainer.getId());

		for (TrainingDay trainingDay : trainingDays) {
			trainingDay.setExerciseRows(this.trainingRepository.readExerciseRowsForTD(trainingDay.getId()));
		}

		return trainingDays;
	}

	public Long deleteTrainingDay(Long trainingDayId) {

		return trainingDayId != null ? this.trainingRepository.deleteTrainingDay(trainingDayId) : null;

	}

	@Transactional
	public String updateTrainingDay(TrainingDay newTrainingDay, Long oldTrainingDayId, String clientUsername, String trainerUsername) {

		this.deleteTrainingDay(oldTrainingDayId);
		
		return this.createTrainingDay(newTrainingDay, clientUsername, trainerUsername);

	}

	private void safeMissingExercises(List<ExerciseRow> exerciseRows) {

		if(exerciseRows == null || exerciseRows.isEmpty()) {
			return;
		}
		
		List<Exercise> exercisesToCheck = new ArrayList<>();
		for (ExerciseRow exerciseRow : exerciseRows) {
			exercisesToCheck.add(exerciseRow.getExercise());
		}

		List<Exercise> allExercises = this.trainingRepository.readAllExercises();

		List<Exercise> missingExercises = this.findMissingExercises(exercisesToCheck, allExercises);

		if (missingExercises != null && !missingExercises.isEmpty()) {
			this.trainingRepository.createExercises(missingExercises);
		}

	}

	private List<Exercise> findMissingExercises(List<Exercise> currentExercises, List<Exercise> allExercises) {

		List<Exercise> missingExercises = new ArrayList<>();

		for (Exercise exercise : currentExercises) {
			if (!allExercises.contains(exercise)) {
				missingExercises.add(exercise);
			}
		}

		return missingExercises;
	}
}
