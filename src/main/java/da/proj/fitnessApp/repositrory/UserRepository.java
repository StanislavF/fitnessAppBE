package da.proj.fitnessApp.repositrory;

import da.proj.fitnessApp.models.User;

public interface UserRepository {
	Long createUser(User user);
	User readUserByUsername (String username);
}
