package da.proj.fitnessApp.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

		return responseText != null ? new ResponseEntity<String>(responseText, HttpStatus.OK)
				: new ResponseEntity<String>(responseText, HttpStatus.BAD_REQUEST);
	}

}
