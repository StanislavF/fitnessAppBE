package da.proj.fitnessApp.models;

public class FoodRow {

	private Long id;
	private Integer no;
	private Food food;
	private String weight;
	private Integer calories;
	private Integer proteins;
	private Integer carbs;
	private Integer fats;
	private String comments;
	
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
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
