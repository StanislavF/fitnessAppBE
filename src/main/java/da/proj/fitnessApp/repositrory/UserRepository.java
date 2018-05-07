package da.proj.fitnessApp.repositrory;

import java.util.List;

import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.TrainerClientRequest;
import da.proj.fitnessApp.models.User;

public interface UserRepository {
	Long createUser(User user);
	
	User readUserByUsername (String username);
	
	boolean checkTrainerClient(String trainserUsername, String clientUsername);
	
	List<SearchUser> readUsers(SearchData data);
	
	Long requestTrainer(Long trainerId, Long clientId);
	
	List<SearchUser> readClientRequestUsers(String trainerUsername);
	
	String checkForExistingRequests(Long trainerId, Long clientId);
	
	void acceptClientRequest(Long trainerId, Long clientId);
	
	void rejectClientRequest(Long trainerId, Long clientId);
}
