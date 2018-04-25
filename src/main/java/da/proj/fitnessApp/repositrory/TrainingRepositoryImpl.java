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
	private DataSource dataSource;

	private NamedParameterJdbcTemplate jdbcTemplate;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

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
	
	public List<Exercise> readAllExercises(){
		
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

	public Long createTrainingDay(TrainingDay trainingDay, String username) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("td_no", trainingDay.getNo())
				.addValue("td_title", trainingDay.getTitle()).addValue("td_date", trainingDay.getDate())
				.addValue("usr_username", username);

		this.jdbcTemplate.update(SQL.CREATE_TRAINING_DAY, parameters, keyHolder);

		return keyHolder != null ? keyHolder.getKey().longValue() : null;
	}
	
	public List<Exercise> getMissingExercises(List<Exercise> exercises) {

		SingleConnectionDataSource scds = null;
		try {
			scds = new SingleConnectionDataSource(this.dataSource.getConnection(), true);
		} catch (SQLException e) {
			throw new DataAccessResourceFailureException("Unable to create single connnection ds", e);
		}

		List<Exercise> results = new ArrayList<>();
		try {
			NamedParameterJdbcTemplate scdsJdbcTemplate = new NamedParameterJdbcTemplate(scds);

			scdsJdbcTemplate.update(SQL.CREATE_TABLE_TEMP_EXERCISE, new MapSqlParameterSource());
			
			List<Map<String, String>> exerciesMap = new ArrayList<>();
			for (Exercise exercise : exercises) {
				Map<String, String> newMap = new HashMap<String, String>();
				newMap.put("ex_name", exercise.getName());
				exerciesMap.add(newMap);
			}
			SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(exerciesMap.toArray());
			scdsJdbcTemplate.batchUpdate(SQL.CREATE_TEMP_EXERCISE, parameters);
			
			scdsJdbcTemplate.query(SQL.READ_MISSING_EXERCISE, (rs) -> { 
				Exercise exercise = new Exercise();
				exercise.setName(rs.getString("ex_name"));
				results.add(exercise);
			});
		} finally {
			scds.destroy();
		}

		return results;
	}
	
	public List<TrainingDay> readTrainingDays(String date, String username) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);

		return this.jdbcTemplate.queryForObject(SQL.READ_USER_USERNAME, parameters, 
				(rs, rowNum) -> { 
					User user = new User();
					user.setAge(rs.getInt("usr_age"));
					user.setUsername(rs.getString("usr_username"));
					user.setFirstName(rs.getString("usr_firstname"));
					user.setLastName(rs.getString("usr_lastname"));
					user.setEmail(rs.getString("usr_email"));
					user.setIsTrainer(rs.getBoolean("usr_is_trainer"));
					user.setPassword(rs.getString("usr_password"));
					user.setWheight(rs.getInt("usr_weight"));
					user.setHeight(rs.getInt("usr_height"));
					user.setPhone(rs.getString("usr_phone"));
					user.setGoal(GoalEnum.valueOf(rs.getString("usr_goal")));
					user.setDescription(rs.getString("usr_description"));
					
					return user;
				});
	}

}
