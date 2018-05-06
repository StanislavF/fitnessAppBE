package da.proj.fitnessApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.repositrory.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public String registerUser(User user) {

		this.userRepository.createUser(user);

		return "CREATED";
	}

	public LogInData logIn(LogInData data) {

		User user = this.userRepository.readUserByUsername(data.getUsername());

		if (user.getPassword().equals(data.getPassword())) {
			LogInData logInData = new LogInData();
			logInData.setIsTrainer(user.getIsTrainer());
			logInData.setUsername(user.getUsername());
			
			return logInData;
		}

		return null;
	}

	public boolean existUsername(String username) {

		return (this.userRepository.readUserByUsername(username) != null) ? true : false;
	}
	
	@Transactional
	public boolean isTrainerAuthorised(String trainerUsername, String clientUsername) {
		
		boolean is_authorised = false;
		User trainer = this.userRepository.readUserByUsername(trainerUsername);
		
		if(trainer.getIsTrainer().equals(true)) {
			is_authorised = true;
		} else if(this.userRepository.checkTrainerClient(trainerUsername, clientUsername)) {
			is_authorised = true;
		}
		
		return is_authorised;
	}
	
	public List<SearchUser> searchUsers(SearchData data){
		
		if(data == null) {
			return null;
		}
		
		return this.userRepository.readUsers(data);
	}
	
	@Transactional
	public String requestTrainer(String clientUsername, String trainerUsername) {
		
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}
		
		Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
		Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();
		
		Long requestId = this.userRepository.requestTrainer(trainerId, clientId);
		
		return "CREATED";
	}
}
