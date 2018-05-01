package da.proj.fitnessApp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import da.proj.fitnessApp.models.Food;
import da.proj.fitnessApp.models.FoodRow;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.repositrory.MealRepository;
import da.proj.fitnessApp.repositrory.TrainingRepository;
import da.proj.fitnessApp.repositrory.UserRepository;

public class MealServiceImpl implements MealService {

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public String createSingleMeal(SingleMeal singleMeal, String clientUsername, String trainerUsername) {

		if (singleMeal == null || clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}

		List<Food> foodsToCheck = new ArrayList<>();
		for (FoodRow foodRow : singleMeal.getFoodRows()) {
			foodsToCheck.add(foodRow.getFood());
		}

		List<Food> allFoods = this.mealRepository.readAllFoods();

		List<Food> missingFoods = this.findMissingFoods(foodsToCheck, allFoods);

		this.mealRepository.createFoods(missingFoods);

		User trainer = this.userRepository.readUserByUsername(trainerUsername);
		User client = this.userRepository.readUserByUsername(clientUsername);

		Long singleMealId = this.mealRepository.createSingleMeal(singleMeal, client.getId(), trainer.getId());

		this.mealRepository.createFoodRow(singleMeal.getFoodRows(), singleMealId);

		return "CREATED";
	}
	
	private List<Food> findMissingFoods(List<Food> currentFoods, List<Food> allFoods) {

		List<Food> missingFoods = new ArrayList<>();

		for (Food food : currentFoods) {
			if (allFoods.indexOf(food) == -1) {
				missingFoods.add(food);
			}
		}

		return missingFoods;
	}

	@Override
	public List<SingleMeal> getAllSingleMealsForUser(String date, String clientUsername, String trainerUsername) {
		if(date == null || date.isEmpty() || clientUsername == null || clientUsername.isEmpty()
				|| trainerUsername == null || trainerUsername.isEmpty()) {
			return null;
		}
		
		User client = this.userRepository.readUserByUsername(clientUsername);
		User trainer = this.userRepository.readUserByUsername(trainerUsername);
		
		List<SingleMeal> singleMeals = this.mealRepository.readSingleMeals(date, client.getId(), trainer.getId());
		
		for(SingleMeal singleMeal : singleMeals) {
			singleMeal.setFoodRows(this.mealRepository.readFoodRowsForSM(singleMeal.getId()));
		}
		
		return singleMeals;
	}

	@Override
	public Long deleteSingleMeal(Long singleMeal) {
		
		return singleMeal != null ? this.mealRepository.deleteSingleMeal(singleMeal) : null;
	}

}
