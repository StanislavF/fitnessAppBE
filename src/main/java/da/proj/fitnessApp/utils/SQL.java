package da.proj.fitnessApp.utils;

public class SQL {
	
	static String sqlPath = "sql";
	
	public final static String CREATE_EXERCISE = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE.sql");
	public final static String CREATE_EXERCISE_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE_ROW.sql");
	public final static String CREATE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "CREATE_TRAINING_DAY.sql");
	public static final String READ_ALL_EXERCISES = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISES.sql");
	public static final String READ_ALL_EXERCISE_ROWS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISE_ROWS.sql");
	public static final String DELETE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "DELETE_TRAINING_DAY.sql");
	
	public final static String CREATE_FOOD = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD.sql");
	public final static String CREATE_FOOD_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD_ROW.sql");
	public final static String CREATE_SINGLE_MEAL = SqlUtils.getClasspathResource(sqlPath, "CREATE_SINGLE_MEAL.sql");
	public final static String READ_ALL_FOODS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_FOODS.sql");
	public final static String READ_SM_USER = SqlUtils.getClasspathResource(sqlPath, "READ_SM_USER.sql");
	public final static String READ_ALL_FOOD_ROWS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_FOOD_ROWS.sql");
	public final static String DELETE_SINGLE_MEAL = SqlUtils.getClasspathResource(sqlPath, "DELETE_SINGLE_MEAL.sql");
	
	public static final String CHECK_TRAINER_CLIENT = SqlUtils.getClasspathResource(sqlPath, "CHECK_TRAINER_CLIENT.sql");
	public final static String CREATE_USER = SqlUtils.getClasspathResource(sqlPath, "CREATE_USER.sql");
	public final static String READ_USER_USERNAME = SqlUtils.getClasspathResource(sqlPath, "READ_USER_USERNAME.sql");
	public static final String READ_TD_USER = SqlUtils.getClasspathResource(sqlPath, "READ_TD_USER.sql");
	public static final String READ_USERS = SqlUtils.getClasspathResource(sqlPath, "READ_USERS.sql");
	
	public static final String INSERT_TC_REQUEST = SqlUtils.getClasspathResource(sqlPath, "INSERT_TC_REQUEST.sql");
}
