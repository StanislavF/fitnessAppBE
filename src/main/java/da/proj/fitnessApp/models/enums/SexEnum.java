package da.proj.fitnessApp.models.enums;

public enum SexEnum {
	BOTH("Both"), 
	MALE("Male"), 
	FEMALE("Female");
	
	private final String value;       

    private SexEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
     }
}
