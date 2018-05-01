package da.proj.fitnessApp.utils;

public class SQL {
	
	static String sqlPath = "/fitnessApp/src/main/resources/sql";
	
	public final static String CREATE_EXERCISE = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE");
	public final static String CREATE_EXERCISE_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE_ROW");
	public final static String CREATE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "CREATE_TRAINING_DAY");
	public static final String READ_ALL_EXERCISES = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISES");
	public static final String READ_ALL_EXERCISE_ROWS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISE_ROWS");
	public static final String DELETE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "DELETE_TRAINING_DAY");
	
	public final static String CREATE_FOOD = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD");
	public final static String CREATE_FOOD_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD_ROW");
	public final static String CREATE_SINGLE_MEAL = SqlUtils.getClasspathResource(sqlPath, "CREATE_SINGLE_MEAL");
	public final static String READ_ALL_FOODS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_FOODS");
	public final static String READ_SM_USER = SqlUtils.getClasspathResource(sqlPath, "READ_SM_USER");
	public final static String READ_ALL_FOOD_ROWS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_FOOD_ROWS");
	public final static String DELETE_SINGLE_MEAL = SqlUtils.getClasspathResource(sqlPath, "DELETE_SINGLE_MEAL");
	
	public static final String CHECK_TRAINER_CLIENT = SqlUtils.getClasspathResource(sqlPath, "CHECK_TRAINER_CLIENT");
	public final static String CREATE_USER = SqlUtils.getClasspathResource(sqlPath, "CREATE_USER");
	public final static String READ_USER_USERNAME = SqlUtils.getClasspathResource(sqlPath, "READ_USER_USERNAME");
	public static final String READ_TD_USER = SqlUtils.getClasspathResource(sqlPath, "READ_TD_USER");
}
