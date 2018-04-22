package da.proj.fitnessApp.models.enums;

public enum GoalEnum {
    FAST_WEIGHT_LOSS("Fast weight loss"),
    WEIGHT_LOSS("Weight loss"),
    MAINTENANCE("Maintenance"),
    WEIGHT_GAIN("Weight gain"),
    FAST_WEIGHT_GAIN("Fast weight gain");
	
	private final String value;       

    private GoalEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
     }
}
