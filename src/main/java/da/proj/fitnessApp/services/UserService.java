package da.proj.fitnessApp.services;

import java.util.List;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.User;

public interface UserService {
	String registerUser(User user);
	boolean existUsername(String username);
	LogInData logIn (LogInData data);
	boolean isTrainerAuthorised(String trainerUsername, String clientUsername);
	List<SearchUser> searchUsers (SearchData data);

}
