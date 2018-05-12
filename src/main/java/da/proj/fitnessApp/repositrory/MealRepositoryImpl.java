package da.proj.fitnessApp.repositrory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import da.proj.fitnessApp.models.Food;
import da.proj.fitnessApp.models.FoodRow;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.models.TrainingDay;
import da.proj.fitnessApp.utils.SQL;

@Repository
public class MealRepositoryImpl implements MealRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public void createFoods(List<Food> foods) {
		List<Map<String, String>> foodsMap = new ArrayList<>();

		for (Food food : foods) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put("fd_name", food.getName());
			foodsMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(foodsMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_FOOD, parameters);
		
	}

	@Override
	public void createFoodRow(List<FoodRow> foodRows, Long sm_id) {
		List<Map<String, String>> foodsMap = new ArrayList<>();

		for (FoodRow foodRow : foodRows) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put("fr_no", String.valueOf(foodRow.getNo()));
			newMap.put("fd_name", foodRow.getFood().getName());
			newMap.put("fr_weight", foodRow.getWeight());
			newMap.put("fr_calories", String.valueOf(foodRow.getCalories()));
			newMap.put("fr_proteins", String.valueOf(foodRow.getProteins()));
			newMap.put("fr_carbs", String.valueOf(foodRow.getCarbs()));
			newMap.put("fr_fats", String.valueOf(foodRow.getFats()));
			newMap.put("fr_sm_id", String.valueOf(sm_id));
			foodsMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(foodsMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_FOOD_ROW, parameters);
	}

	@Override
	public Long createSingleMeal(SingleMeal singleMeal, Long clientId, Long trainerId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("sm_no", singleMeal.getNo())
				.addValue("sm_title", singleMeal.getTitle()).addValue("sm_date", singleMeal.getDate())
				.addValue("sm_calories", singleMeal.getCalories()).addValue("sm_proteins", singleMeal.getProteins())
				.addValue("sm_carbs", singleMeal.getCarbs()).addValue("sm_fats", singleMeal.getFats())
				.addValue("sm_client_id", clientId).addValue("sm_trainer_id", trainerId);

		this.jdbcTemplate.update(SQL.CREATE_SINGLE_MEAL, parameters, keyHolder);

		return keyHolder != null ? keyHolder.getKey().longValue() : null;
	}

	@Override
	public List<Food> readAllFoods() {
		List<Food> results = new ArrayList<>();
		this.jdbcTemplate.query(SQL.READ_ALL_FOODS, (rs) -> {
			Food food = new Food();
			food.setName(rs.getString("fd_name"));
			results.add(food);
		});

		return results;
	}

	@Override
	public List<SingleMeal> readSingleMeals(String date, Long clientId, Long trainerId) {
		List<SingleMeal> results = new ArrayList<>();
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("sm_client_id", clientId)
				.addValue("sm_trainer_id", trainerId)
				.addValue("sm_date", date);

		this.jdbcTemplate.query(SQL.READ_SM_USER, parameters, 
				(rs) -> { 
					SingleMeal singleMeal = new SingleMeal();
					singleMeal.setId(rs.getLong("sm_id"));
					singleMeal.setDate(rs.getString("sm_date"));
					singleMeal.setId(rs.getLong("sm_id"));
					singleMeal.setNo(rs.getInt("sm_no"));
					singleMeal.setTitle(rs.getString("sm_title"));
					singleMeal.setCalories(rs.getInt("sm_calories"));
					singleMeal.setProteins(rs.getInt("sm_proteins"));
					singleMeal.setCarbs(rs.getInt("sm_carbs"));
					singleMeal.setFats(rs.getInt("sm_fats"));
					results.add(singleMeal);
				});
		
		return results;
	}

	@Override
	public List<FoodRow> readFoodRowsForSM(Long singleMealId) {
		List<FoodRow> results = new ArrayList<>();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("fr_sm_id", singleMealId);

		this.jdbcTemplate.query(SQL.READ_ALL_FOOD_ROWS, parameters, (rs) -> {
			FoodRow foodRow = new FoodRow();
			foodRow.setComment(rs.getString("fr_comment"));
			Food food = new Food();
			food.setName(rs.getString("fd_name"));
			foodRow.setFood(food);
			foodRow.setId(rs.getLong("fr_id"));
			foodRow.setNo(rs.getString("fr_no"));
			foodRow.setWeight(rs.getString("fr_weight"));
			foodRow.setCalories(rs.getInt("fr_calories"));
			foodRow.setProteins(rs.getInt("fr_proteins"));
			foodRow.setCarbs(rs.getInt("fr_carbs"));
			foodRow.setFats(rs.getInt("fr_fats"));
			results.add(foodRow);

		});

		return results;
	}

	@Override
	public void deleteSingleMeal(Long singleMealId) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("sm_id", singleMealId);

		this.jdbcTemplate.update(SQL.DELETE_SINGLE_MEAL, parameters);

	}

}
