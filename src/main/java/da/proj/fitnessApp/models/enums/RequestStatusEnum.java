package da.proj.fitnessApp.models.enums;

public enum RequestStatusEnum {
	REQUESTED("REQUESTED"),
	ACCEPTED("ACCEPTED"),
	REJECTED("REJECTED"),
	CANCELED("CANCELED");

	private final String value;       

    private RequestStatusEnum(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
     }
	
}
