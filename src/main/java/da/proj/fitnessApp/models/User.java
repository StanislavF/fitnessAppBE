package da.proj.fitnessApp.models;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import da.proj.fitnessApp.models.enums.GoalEnum;
import da.proj.fitnessApp.models.enums.SexEnum;

public class User {
	
	private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private SexEnum sex;
    private String email;
    private String phone;
    private Integer weight;
    private Integer height;
    private GoalEnum goal;
    private Boolean isTrainer;
    private String description;
    private String image;
    private List<User> myClients;
    private List<User> myTrainers;
    private List<TrainingDay> myTrainingDays;
    private List<SingleMeal> mySingleMeals;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public SexEnum getSex() {
		return sex;
	}
	public void setSex(SexEnum sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public GoalEnum getGoal() {
		return goal;
	}
	public void setGoal(GoalEnum goal) {
		this.goal = goal;
	}
	public Boolean getIsTrainer() {
		return isTrainer;
	}
	public void setIsTrainer(Boolean isTrainer) {
		this.isTrainer = isTrainer;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<User> getMyClients() {
		return myClients;
	}
	public void setMyClients(List<User> myClients) {
		this.myClients = myClients;
	}
	public List<User> getMyTrainers() {
		return myTrainers;
	}
	public void setMyTrainers(List<User> myTrainers) {
		this.myTrainers = myTrainers;
	}
	public List<TrainingDay> getMyTrainingDays() {
		return myTrainingDays;
	}
	public void setMyTrainingDays(List<TrainingDay> myTrainingDays) {
		this.myTrainingDays = myTrainingDays;
	}
	public List<SingleMeal> getMySingleMeals() {
		return mySingleMeals;
	}
	public void setMySingleMeals(List<SingleMeal> mySingleMeals) {
		this.mySingleMeals = mySingleMeals;
	}
    
    

}
