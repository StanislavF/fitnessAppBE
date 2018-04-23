package da.proj.fitnessApp.repositrory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.TrainingDay;
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

	// ToDo make for lisi
	public Long createExerciseRow(ExerciseRow exerciseRow, Long td_id) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("er_no", exerciseRow.getNo())
				.addValue("er_ex_id", exerciseRow.getExercise().getId()).addValue("er_sets", exerciseRow.getSets())
				.addValue("er_reps", exerciseRow.getReps()).addValue("er_weight", exerciseRow.getWeight())
				.addValue("er_comment", exerciseRow.getComment()).addValue("er_td_id", td_id);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(SQL.CREATE_EXERCISE_ROW, parameters, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public Long createTrainingDay(TrainingDay trainingDay, Long clientUserId) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("td_no", trainingDay.getNo())
				.addValue("td_title", trainingDay.getTitle()).addValue("td_date", trainingDay.getDate())
				.addValue("td_usr_id", clientUserId);

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(SQL.CREATE_TRAINING_DAY, parameters, keyHolder);

		return keyHolder.getKey().longValue();
	}

}
