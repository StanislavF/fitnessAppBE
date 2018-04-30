package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.SingleMeal;


public interface MealService {

	String createSingleMeal(SingleMeal singleMeal, String clientUsername, String trainerUsername);

	public List<SingleMeal> getAllSingleMealsForUser(String date, String clientUsername, String trainerUsername);

	public Long deleteSingleMeal(Long singleMeal);

}
