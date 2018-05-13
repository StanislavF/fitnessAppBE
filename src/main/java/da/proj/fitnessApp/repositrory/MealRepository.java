package da.proj.fitnessApp.repositrory;

import java.util.List;

import da.proj.fitnessApp.models.Food;
import da.proj.fitnessApp.models.FoodRow;
import da.proj.fitnessApp.models.SingleMeal;

public interface MealRepository {
	
	void createFoods(List<Food> foods);

	void createFoodRow(List<FoodRow> foodRows, Long sm_id);

	Long createSingleMeal(SingleMeal singleMeal, Long clientId, Long trainerId);

	List<Food> readAllFoods();

	List<SingleMeal> readSingleMeals(String date, Long clientId, Long trainerId);

	List<FoodRow> readFoodRowsForSM(Long singleMealId);

	void deleteSingleMeal(Long singleMealId);
	
	void createComment(Long foodRowId, String comment);
}
