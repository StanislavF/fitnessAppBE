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
import da.proj.fitnessApp.models.TrainerClientRequest;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.models.enums.RequestStatusEnum;
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
	public String getTrainerClientStatus(String trainerUsername, String clientUsername) {

		User trainer = this.userRepository.readUserByUsername(trainerUsername);
		String request_status = null;
		if (trainer.getIsTrainer().equals(true)) {
			request_status = this.userRepository.checkTrainerClient(trainer.getId(), clientUsername);
		}

		return request_status;
	}

	public List<SearchUser> searchUsers(SearchData data) {

		if (data == null) {
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

		String result = this.userRepository.checkForExistingRequests(trainerId, clientId);

		if (result != null) {
			return result;
		} else {
			Long requestId = this.userRepository.requestTrainer(trainerId, clientId);
		}

		return "CREATED";
	}

	@Transactional
	public List<SearchUser> getClientRequestUsers(String trainerUsername) {

		if (trainerUsername == null || trainerUsername.isEmpty()) {
			return null;
		}

		return this.userRepository.readClientRequestUsers(trainerUsername);

	}

	@Override
	@Transactional
	public boolean acceptClientRequest(String clientUsername, String trainerUsername) {
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return false;
		}
		try {
			Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
			Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

			this.userRepository.acceptClientRequest(trainerId, clientId);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean rejectClientRequest(String clientUsername, String trainerUsername) {
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return false;
		}
		try {
			Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
			Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

			this.userRepository.rejectClientRequest(trainerId, clientId);

			return true;
		} catch (Exception ex) {
			
			return false;
		}
	}

	@Override
	@Transactional
	public List<String> getClientsUsername(String trainerUsername) {

		if (trainerUsername == null || trainerUsername.isEmpty()) {
			return null;
		}

		Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

		return this.userRepository.readClientsUsername(trainerId);

	}

	@Override
	public List<String> getTrainersUsername(String clientUsername) {

		if (clientUsername == null || clientUsername.isEmpty()) {
			return null;
		}

		Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();

		return this.userRepository.readTrainersUsername(clientId);

	}

	@Override
	public User getUserDataByUsername(String username) {

		if (username == null || username.isEmpty()) {
			return null;
		}

		return this.userRepository.readUserByUsername(username);
	}

	@Override
	public ResponseEntity<String> createUnauthorizedTrainerResponse(String authResponse) {
		if (authResponse == null || authResponse.equals(RequestStatusEnum.REQUESTED.getValue())
				|| authResponse.equals(RequestStatusEnum.REJECTED.getValue())) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		} else if (authResponse.equals(RequestStatusEnum.CANCELED.getValue())) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
		
		return null;
	}

	@Override
	public String cancelTrainerClient(String clientUsername, String trainerUsername) {
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}
		
		Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
		Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

		this.userRepository.cancelTrainerClient(clientId, trainerId);
		
		return "OK";

	}

	@Override
	public String removeClientFromTrainerVisability(String clientUsername, String trainerUsername) {
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}
		
		Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
		Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

		this.userRepository.removeClientFromTrainerVisability(clientId, trainerId);
		
		return "OK";
	}

	@Override
	public String removeTrainerFromClientVisability(String clientUsername, String trainerUsername) {
		if (clientUsername == null || clientUsername.isEmpty() || trainerUsername == null
				|| trainerUsername.isEmpty()) {
			return null;
		}
		
		Long clientId = this.userRepository.readUserByUsername(clientUsername).getId();
		Long trainerId = this.userRepository.readUserByUsername(trainerUsername).getId();

		this.userRepository.removeTrainerFromClientVisability(clientId, trainerId);
		
		return "OK";
	}

}
