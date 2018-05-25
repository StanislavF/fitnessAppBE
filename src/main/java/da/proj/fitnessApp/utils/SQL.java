package da.proj.fitnessApp.utils;

public class SQL {
	
	static String sqlPath = "sql";
	
	public final static String CREATE_EXERCISE = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE.sql");
	public final static String CREATE_EXERCISE_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_EXERCISE_ROW.sql");
	public final static String CREATE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "CREATE_TRAINING_DAY.sql");
	public final static String CREATE_ER_COMMENT = SqlUtils.getClasspathResource(sqlPath, "CREATE_ER_COMMENT.sql");
	public static final String READ_ALL_EXERCISES = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISES.sql");
	public static final String READ_ALL_EXERCISE_ROWS = SqlUtils.getClasspathResource(sqlPath, "READ_ALL_EXERCISE_ROWS.sql");
	public static final String DELETE_TRAINING_DAY = SqlUtils.getClasspathResource(sqlPath, "DELETE_TRAINING_DAY.sql");
	
	public final static String CREATE_FOOD = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD.sql");
	public final static String CREATE_FOOD_ROW = SqlUtils.getClasspathResource(sqlPath, "CREATE_FOOD_ROW.sql");
	public final static String CREATE_SINGLE_MEAL = SqlUtils.getClasspathResource(sqlPath, "CREATE_SINGLE_MEAL.sql");
	public final static String CREATE_FR_COMMENT = SqlUtils.getClasspathResource(sqlPath, "CREATE_FR_COMMENT.sql");
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
	public static final String READ_CLIENT_REQUEST_USERS = SqlUtils.getClasspathResource(sqlPath, "READ_CLIENT_REQUEST_USERS.sql");
	public static final String CHECK_EXISTING_CLIENT_REQUESTS = SqlUtils.getClasspathResource(sqlPath, "CHECK_EXISTING_CLIENT_REQUESTS.sql");
	public static final String UPDATE_TC_REQUEST_STATUS= SqlUtils.getClasspathResource(sqlPath, "UPDATE_TC_REQUEST_STATUS.sql");
	public static final String READ_CLIENTS = SqlUtils.getClasspathResource(sqlPath, "READ_CLIENTS.sql");
	public static final String READ_TRAINERS= SqlUtils.getClasspathResource(sqlPath, "READ_TRAINERS.sql");
	public static final String READ_IMAGE = SqlUtils.getClasspathResource(sqlPath, "READ_IMAGE.sql");
	public static final String UPDATE_TC_UNSET_IS_VISIBLE_CLIENT= SqlUtils.getClasspathResource(sqlPath, "UPDATE_TC_UNSET_IS_VISIBLE_CLIENT.sql");
	public static final String UPDATE_TC_UNSET_IS_VISIBLE_TRAINER= SqlUtils.getClasspathResource(sqlPath, "UPDATE_TC_UNSET_IS_VISIBLE_TRAINER.sql");
	public static final String UPDATE_PASSWORD= SqlUtils.getClasspathResource(sqlPath, "UPDATE_PASSWORD.sql");
	public static final String UPDATE_EMAIL= SqlUtils.getClasspathResource(sqlPath, "UPDATE_EMAIL.sql");
	public static final String UPDATE_USER = SqlUtils.getClasspathResource(sqlPath, "UPDATE_USER.sql");
	public static final String UPDATE_IMAGE = SqlUtils.getClasspathResource(sqlPath, "UPDATE_IMAGE.sql");

	
}
