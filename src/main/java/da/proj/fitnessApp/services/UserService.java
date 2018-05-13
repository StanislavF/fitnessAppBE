package da.proj.fitnessApp.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.models.enums.RequestStatusEnum;

public interface UserService {
	String registerUser(User user);
	boolean existUsername(String username);
	LogInData logIn (LogInData data);
	String getTrainerClientStatus(String trainerUsername, String clientUsername);
	List<SearchUser> searchUsers (SearchData data);
	String requestTrainer(String clientUsername, String trainerUsername);
	List<SearchUser> getClientRequestUsers(String trainerUsername);
	boolean acceptClientRequest(String clientUsername, String trainerUsername);
	boolean rejectClientRequest(String clientUsername, String trainerUsername);
	List<String> getClientsUsername(String trainerUsername);
	List<String> getTrainersUsername(String clientUsername);
	User getUserDataByUsername(String username);
	ResponseEntity<String> createUnauthorizedTrainerResponse(String authResponse);
	String cancelTrainerClient(String clientUsername, String trainerUsername);
	String removeClientFromTrainerVisability(String clientUsername, String trainerUsername);
	String removeTrainerFromClientVisability(String clientUsername, String trainerUsername);
}
