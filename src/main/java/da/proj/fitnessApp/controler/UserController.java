package da.proj.fitnessApp.controler;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.SingleMeal;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody User user) {

		String responseText;

		if (this.userService.existUsername(user.getUsername())) {
			responseText = "Username Exist";
			return new ResponseEntity<String>(responseText, HttpStatus.CONFLICT);
		}

		responseText = this.userService.registerUser(user);

		ResponseEntity<String> response = new ResponseEntity<String>(responseText, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/log-in", method = RequestMethod.POST)
	public ResponseEntity<LogInData> logIn(@RequestBody LogInData data) {

		LogInData responseData = this.userService.logIn(data);

		return (responseData != null) ? new ResponseEntity<>(responseData, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/search-user", method = RequestMethod.POST)
	public ResponseEntity<List<SearchUser>> searchUser(@RequestBody SearchData data) {

		List<SearchUser> responseData = this.userService.searchUsers(data);

		return (responseData != null) ? new ResponseEntity<>(responseData, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/request-trainer", method = RequestMethod.GET)
	public ResponseEntity<String> requestTrainer(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String responseText = this.userService.requestTrainer(clientUsername, trainerUsername);

		return responseText != "CREATED" ? new ResponseEntity<String>(responseText, HttpStatus.ALREADY_REPORTED)
				: new ResponseEntity<String>(responseText, HttpStatus.OK);
	}

	@RequestMapping(value = "/get-client-requests", method = RequestMethod.GET)
	public ResponseEntity<List<SearchUser>> getClientsRequests(@RequestParam String trainerUsername) {

		List<SearchUser> response = this.userService.getClientRequestUsers(trainerUsername);

		return response != null ? new ResponseEntity<List<SearchUser>>(response, HttpStatus.OK)
				: new ResponseEntity<List<SearchUser>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/accept-client-request", method = RequestMethod.PUT)
	public ResponseEntity<String> acceptClientRequest(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		if (this.userService.acceptClientRequest(clientUsername, trainerUsername)) {
			return new ResponseEntity<String>("ACCEPTED", HttpStatus.OK);
		}

		return new ResponseEntity<String>("ERROR", HttpStatus.OK);
	}

	@RequestMapping(value = "/reject-client-request", method = RequestMethod.PUT)
	public ResponseEntity<String> rejectClientRequest(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		if (this.userService.rejectClientRequest(clientUsername, trainerUsername)) {
			return new ResponseEntity<String>("ACCEPTED", HttpStatus.OK);
		}

		return new ResponseEntity<String>("ERROR", HttpStatus.OK);
	}

	@RequestMapping(value = "/get-clients", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getClientUsernames(@RequestParam String trainerUsername) {

		List<String> response = this.userService.getClientsUsername(trainerUsername);

		return response != null ? new ResponseEntity<List<String>>(response, HttpStatus.OK)
				: new ResponseEntity<List<String>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/get-trainers", method = RequestMethod.GET)
	public ResponseEntity<List<String>> getTrainerUsernames(@RequestParam String clientUsername) {

		List<String> response = this.userService.getTrainersUsername(clientUsername);

		return response != null ? new ResponseEntity<List<String>>(response, HttpStatus.OK)
				: new ResponseEntity<List<String>>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/get-user-data", method = RequestMethod.GET)
	public ResponseEntity<User> getUserData(@RequestParam String username) {

		User response = this.userService.getUserDataByUsername(username);

		return response != null ? new ResponseEntity<User>(response, HttpStatus.OK)
				: new ResponseEntity<User>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/cancel-trainer-cliet", method = RequestMethod.PUT)
	public ResponseEntity<String> cancelTrainerClient(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String response = this.userService.cancelTrainerClient(clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/get-trainer-cliet-status", method = RequestMethod.GET)
	public ResponseEntity<String> getTrainerClientStatus(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String response = this.userService.getTrainerClientStatus(trainerUsername, clientUsername);

		return response != null ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/remove-client-from-trainer-visability", method = RequestMethod.PUT)
	public ResponseEntity<String> removeClientFromTrainerVisability(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String response = this.userService.removeClientFromTrainerVisability(clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/remove-trainer-from-client-visability", method = RequestMethod.PUT)
	public ResponseEntity<String> removeTrainerFromClientVisability(@RequestParam String trainerUsername,
			@RequestParam String clientUsername) {

		String response = this.userService.removeTrainerFromClientVisability(clientUsername, trainerUsername);

		return response != null ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update-user", method = RequestMethod.POST, consumes = { "multipart/form-data",
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> updateUser(@RequestParam String user,
			@RequestPart(required = false) MultipartFile image) {
		
		ObjectMapper mapper = new ObjectMapper();
		User userObj = null;
		InputStream imageStream = null;
		try {
			userObj = mapper.readValue(user, User.class);
			imageStream = image != null ? image.getInputStream() : null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String response = this.userService.updateUser(userObj, imageStream);

		return response != null ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update-user/password", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUserPassowrd(@RequestParam String username, @RequestParam String oldPassword,
			@RequestParam String newPassword) {

		String response = this.userService.updateUserPassword(username, oldPassword, newPassword);

		return response.equals("OK") ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update-user/email", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUserEmail(@RequestParam String username, @RequestParam String password,
			@RequestParam String email) {

		String response = this.userService.updateUserEmail(username, password, email);

		return response.equals("OK") ? new ResponseEntity<String>(response, HttpStatus.OK)
				: new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
	}

}
