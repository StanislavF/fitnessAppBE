package da.proj.fitnessApp.services;

import org.springframework.http.ResponseEntity;

import da.proj.fitnessApp.models.LogInData;
import da.proj.fitnessApp.models.User;

public interface UserService {
	String registerUser(User user);
	boolean existUsername(String username);
	LogInData logIn (LogInData data);

}
