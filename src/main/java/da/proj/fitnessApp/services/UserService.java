package da.proj.fitnessApp.services;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.User;

public interface UserService {
	String registerUser(User user);
	boolean existUsername(String username);
	LogInData logIn (LogInData data);
	public boolean isTrainerAuthorised(String trainerUsername, String clientUsername);

}
