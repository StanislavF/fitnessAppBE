package da.proj.fitnessApp.repositrory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import da.proj.fitnessApp.models.Exercise;
import da.proj.fitnessApp.models.ExerciseRow;
import da.proj.fitnessApp.models.Food;
import da.proj.fitnessApp.models.FoodRow;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.utils.SQL;

public class MealRepositoryImpl implements MealRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public void createFood(List<Food> foods) {
		List<Map<String, String>> exerciesMap = new ArrayList<>();

		for (Food food : foods) {
			Map<String, String> newMap = new HashMap<String, String>();
			newMap.put("td_name", food.getName());
			exerciesMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(exerciesMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_EXERCISE, parameters);
		
	}

	@Override
	public void createFoodRow(List<FoodRow> foodRows, Long sm_id) {
		List<Map<String, String>> exerciesMap = new ArrayList<>();

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
			exerciesMap.add(newMap);
		}

		SqlParameterSource[] parameters = SqlParameterSourceUtils.createBatch(exerciesMap.toArray());

		this.jdbcTemplate.batchUpdate(SQL.CREATE_EXERCISE_ROW, parameters);
	}

	@Override
	public Long createSingleMeal(SingleMeal singleMeal, Long clientId, Long trainerId) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FoodRow> readFoodRowsForSM(Long singleMealId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteSingleMeal(Long singleMealId) {
		// TODO Auto-generated method stub
		return null;
	}

}
