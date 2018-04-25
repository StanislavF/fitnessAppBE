package da.proj.fitnessApp.utils;

public class SQL {
	
	public final static String CREATE_USER = "INSERT INTO `user`(`usr_username`, "
														+ "`usr_firstname`, "
														+ "`usr_lastname`, "
														+ "`usr_email`, "
														+ "`usr_is_trainer`, "
														+ "`usr_password`, "
														+ "`usr_age`, "
														+ "`usr_weight`, "
														+ "`usr_height`, "
														+ "`usr_phone`, "
														+ "`usr_goal`, "
														+ "`usr_description`) "
										 + "VALUES (:usr_username,"
										 		 + ":usr_firstname,"
										 		 + ":usr_lastname,"
										 		 + ":usr_email,"
										 		 + ":usr_is_trainer,"
										 		 + ":usr_password,"
										 		 + ":usr_age,"
										 		 + ":usr_weight,"
										 		 + ":usr_height,"
										 		 + ":usr_phone,"
										 		 + ":usr_goal,"
										 		 + ":usr_description)";
	
	public final static String CREATE_EXERCISE = "INSERT INTO `exercise`(`ex_name`) VALUES (:ex_name)";
	
	public final static String CREATE_EXERCISE_ROW = "INSERT INTO `exercise_row`(`er_no`, `er_ex_id`, `er_sets`, `er_reps`, `er_weight`, `er_comment`, `er_td_id`)" + 
			"SELECT :er_no, exercise.ex_id , :er_sets, :er_reps, :er_weight, :er_comment, :er_td_id" + 
			"FROM exercise" + 
			"WHERE exercise.ex_name = :ex_name";
	
	public final static String CREATE_TRAINING_DAY = "INSERT INTO `training_day`(`td_id`, `td_no`, `td_title`, `td_date`, `td_usr_id`)" + 
			"SELECT :td_id, :td_no, :td_title, :td_date, user.usr_id" + 
			"FROM user" + 
			"WHERE user.usr_username=:usr_username";
	
	public static final String READ_ALL_EXERCISES = "SELECT * FROM `exercise`";

	public final static String READ_USER_USERNAME = "SELECT COUNT(usr_id) AS count FROM `user` WHERE usr_username = :username";
	public static final String READ_TRAINING_DAYS_USER = "S";
}
