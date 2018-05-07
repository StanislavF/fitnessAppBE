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
	String requestTrainer(String clientUsername, String trainerUsername);
	List<SearchUser> getClientRequestUsers(String trainerUsername);
	boolean acceptClientRequest(String clientUsername, String trainerUsername);
	boolean rejectClientRequest(String clientUsername, String trainerUsername);
	List<String> getClientsUsername(String trainerUsername);
	List<String> getTrainersUsername(String clientUsername);
	User getUserDataByUsername(String username);
}
