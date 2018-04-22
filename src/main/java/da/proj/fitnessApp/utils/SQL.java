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
	
	public final static String READ_USER_USERNAME = "SELECT COUNT(usr_id) AS count FROM `user` WHERE usr_username = :username";

}
