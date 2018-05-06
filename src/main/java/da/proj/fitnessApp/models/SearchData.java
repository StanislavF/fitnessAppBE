package da.proj.fitnessApp.models;

import da.proj.fitnessApp.models.enums.SexEnum;

public class SearchData {

    private String username;
    private Boolean isTrainer;
    private String firstName;
    private String lastName;
    private Integer fromAge;
    private Integer toAge;
    private String sex;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getIsTrainer() {
		return isTrainer;
	}
	public void setIsTrainer(Boolean isTrainer) {
		this.isTrainer = isTrainer;
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
	public Integer getFromAge() {
		return fromAge;
	}
	public void setFromAge(Integer fromAge) {
		this.fromAge = fromAge;
	}
	public Integer getToAge() {
		return toAge;
	}
	public void setToAge(Integer toAge) {
		this.toAge = toAge;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
    
    
	
}
