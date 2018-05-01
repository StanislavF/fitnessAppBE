package da.proj.fitnessApp.models;

import java.util.List;

public class SingleMeal {

	private Long id;
	private Integer no;
	private String title;
	private String date;
	private Integer calories;
	private Integer proteins;
	private Integer carbs;
	private Integer fats;
	private List<FoodRow> foodRows;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getCalories() {
		return calories;
	}
	public void setCalories(Integer calories) {
		this.calories = calories;
	}
	public Integer getProteins() {
		return proteins;
	}
	public void setProteins(Integer proteins) {
		this.proteins = proteins;
	}
	public Integer getCarbs() {
		return carbs;
	}
	public void setCarbs(Integer carbs) {
		this.carbs = carbs;
	}
	public Integer getFats() {
		return fats;
	}
	public void setFats(Integer fats) {
		this.fats = fats;
	}
	public List<FoodRow> getFoodRows() {
		return foodRows;
	}
	public void setFoodRows(List<FoodRow> foodRows) {
		this.foodRows = foodRows;
	}
	
	
}
