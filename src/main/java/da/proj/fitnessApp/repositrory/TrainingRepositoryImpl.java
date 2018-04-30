package da.proj.fitnessApp.repositrory;

import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.models.enums.GoalEnum;
import da.proj.fitnessApp.utils.SQL;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void createExercises(List<Exercise> exercises) {

		List<Map<String, String>> exerciesMap = new ArrayList<>();

		for (Exercise exercise : exercises) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put("ex_name", exercise.getName());
			exerciesMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(exerciesMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_EXERCISE, parameters);

	}
	
	public List<Exercise> readAllExercises() {

		List<Exercise> results = new ArrayList<>();
		this.jdbcTemplate.query(SQL.READ_ALL_EXERCISES, (rs) -> {
			Exercise exercise = new Exercise();
			exercise.setName(rs.getString("ex_name"));
			results.add(exercise);
		});

		return results;
	}

	// ToDo make for lisi
	public void createExerciseRow(List<ExerciseRow> exerciseRows, Long td_id) {

		List<Map<String, String>> exerciesMap = new ArrayList<>();

		for (ExerciseRow exerciseRow : exerciseRows) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put("er_no", String.valueOf(exerciseRow.getNo()));
			newMap.put("ex_name", exerciseRow.getExercise().getName());
			newMap.put("er_sets", exerciseRow.getSets());
			newMap.put("er_reps", exerciseRow.getReps());
			newMap.put("er_weight", exerciseRow.getWeight());
			newMap.put("er_comment", exerciseRow.getComment());
			newMap.put("er_td_id", String.valueOf(td_id));
			exerciesMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(exerciesMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_EXERCISE_ROW, parameters);
	}

	public Long createTrainingDay(TrainingDay trainingDay, Long clientId, Long trainerId) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("td_no", trainingDay.getNo())
				.addValue("td_title", trainingDay.getTitle()).addValue("td_date", trainingDay.getDate())
				.addValue("td_client_id", clientId).addValue("td_trainer_id", trainerId);

		this.jdbcTemplate.update(SQL.CREATE_TRAINING_DAY, parameters, keyHolder);

		return keyHolder != null ? keyHolder.getKey().longValue() : null;
	}
	
	public List<TrainingDay> readTrainingDays(String date, Long clientId, Long trainerId) {
		
		List<TrainingDay> results = new ArrayList<>();
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("td_client_id", clientId)
				.addValue("td_trainer_id", trainerId)
				.addValue("td_date", date);

		this.jdbcTemplate.query(SQL.READ_TD_USER, parameters, 
				(rs) -> { 
					TrainingDay trainingDay = new TrainingDay();
					trainingDay.setDate(rs.getString("td_date"));
					trainingDay.setId(rs.getLong("td_id"));
					trainingDay.setNo(rs.getInt("td_no"));
					trainingDay.setTitle(rs.getString("td_title"));
					results.add(trainingDay);
					
				});
		
		return results;
	}
	
	public List<ExerciseRow> readExerciseRowsForTD(Long trainingDayId) {

		List<ExerciseRow> results = new ArrayList<>();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("er_td_id", trainingDayId);

		this.jdbcTemplate.query(SQL.READ_ALL_EXERCISE_ROWS, parameters, (rs) -> {
			ExerciseRow exerciseRow = new ExerciseRow();
			exerciseRow.setComment(rs.getString("er_comment"));
			Exercise exercise = new Exercise();
			exercise.setName(rs.getString("ex_name"));
			exerciseRow.setExercise(exercise);
			exerciseRow.setId(rs.getLong("er_id"));
			exerciseRow.setNo(rs.getInt("er_no"));
			exerciseRow.setReps(rs.getString("er_reps"));
			exerciseRow.setSets(rs.getString("er_sets"));
			exerciseRow.setWeight(rs.getString("er_weight"));
			results.add(exerciseRow);

		});

		return results;
	}
	
	public Long deleteTrainingDay(Long trainingDayId) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("td_id", trainingDayId);

		this.jdbcTemplate.update(SQL.DELETE_TRAINING_DAY, parameters, keyHolder);

		return keyHolder != null ? keyHolder.getKey().longValue() : null;
	}

}
