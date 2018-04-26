package da.proj.fitnessApp.repositrory;

import java.math.BigInteger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.models.enums.GoalEnum;
import da.proj.fitnessApp.utils.SQL;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private DataSource dataSource;

	private NamedParameterJdbcTemplate jdbcTemplate;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Long createUser(User user) {
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_username", user.getUsername())
				.addValue("usr_firstname", user.getFirstName())
				.addValue("usr_lastname", user.getLastName())
				.addValue("usr_email", user.getEmail())
				.addValue("usr_is_trainer", user.getIsTrainer())
				.addValue("usr_password", user.getPassword())
				.addValue("usr_age", user.getAge())
				.addValue("usr_weight", user.getWheight())
				.addValue("usr_height", user.getHeight())
				.addValue("usr_phone", user.getPhone())
				.addValue("usr_goal", user.getGoal())
				.addValue("usr_description", user.getDescription());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(SQL.CREATE_USER, parameters, keyHolder);
		
		return keyHolder.getKey().longValue();
	}
	
	public User readUserByUsername(String username) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);

		return this.jdbcTemplate.queryForObject(SQL.READ_USER_USERNAME, parameters, 
				(rs, rowNum) -> { 
					User user = new User();
					user.setAge(rs.getInt("usr_age"));
					user.setUsername(rs.getString("usr_username"));
					user.setFirstName(rs.getString("usr_firstname"));
					user.setLastName(rs.getString("usr_lastname"));
					user.setEmail(rs.getString("usr_email"));
					user.setIsTrainer(rs.getBoolean("usr_is_trainer"));
					user.setPassword(rs.getString("usr_password"));
					user.setWheight(rs.getInt("usr_weight"));
					user.setHeight(rs.getInt("usr_height"));
					user.setPhone(rs.getString("usr_phone"));
					user.setGoal(GoalEnum.valueOf(rs.getString("usr_goal")));
					user.setDescription(rs.getString("usr_description"));
					
					return user;
				});

	}
	
	public boolean checkTrainerClient(String trainserUsername, String clientUsername) {
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("trainer_username", trainserUsername)
				.addValue("client_username", clientUsername)
				.addValue("tc_request_status", "ACCEPTED");
		
		return this.jdbcTemplate.queryForObject(SQL.CHECK_TRAINER_CLIENT, parameters, 
				(rs, rowNum) -> { 
					return rowNum > 0 ? true : false;
				});
		
	}
}
