package da.proj.fitnessApp.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ResponseEntity<String> index(@RequestBody User user) {

		String responseText;

		if (this.userService.existUsername(user.getUsername())) {
			responseText = "Username Exist";
			return new ResponseEntity<String>(responseText, HttpStatus.CONFLICT);
		}

		responseText = this.userService.registerUser(user);

		return new ResponseEntity<String>(responseText, HttpStatus.OK);
	}

	@RequestMapping(value = "/log-in", method = RequestMethod.GET)
	public ResponseEntity<LogInData> index(@RequestBody LogInData data) {

		LogInData responseData = this.userService.logIn(data);

		return (responseData != null) ? new ResponseEntity<>(responseData, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

}
