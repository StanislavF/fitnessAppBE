package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.SingleMeal;


public interface MealService {

	String createSingleMeal(SingleMeal singleMeal, String clientUsername, String trainerUsername);

	List<SingleMeal> getAllSingleMealsForUser(String date, String clientUsername, String trainerUsername);

	boolean deleteSingleMeal(Long singleMeal);
	
	String updateSingleMeal(SingleMeal newSingleMeal, Long oldSingleMealId, String clientUsername, String trainerUsername);
	
	String createComment(Long foodRowId, String comment);
}
