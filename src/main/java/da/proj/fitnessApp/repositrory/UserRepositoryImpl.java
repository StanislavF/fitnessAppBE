package da.proj.fitnessApp.repositrory;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import da.proj.fitnessApp.models.SearchData;
import da.proj.fitnessApp.models.SearchUser;
import da.proj.fitnessApp.models.TrainerClientRequest;
import da.proj.fitnessApp.models.User;
import da.proj.fitnessApp.models.enums.GoalEnum;
import da.proj.fitnessApp.models.enums.RequestStatusEnum;
import da.proj.fitnessApp.models.enums.SexEnum;
import da.proj.fitnessApp.utils.SQL;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Value("${date.format}")
	private String dateFormat;

	@Autowired
	private DataSource dataSource;

	private NamedParameterJdbcTemplate jdbcTemplate;

	@PostConstruct
	private void postConstruct() {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	private String WILDCARD="%";

	public Long createUser(User user) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("usr_username", user.getUsername())
				.addValue("usr_firstname", user.getFirstName()).addValue("usr_lastname", user.getLastName())
				.addValue("usr_email", user.getEmail()).addValue("usr_is_trainer", user.getIsTrainer())
				.addValue("usr_password", user.getPassword()).addValue("usr_age", user.getAge())
				.addValue("usr_weight", user.getWheight()).addValue("usr_height", user.getHeight())
				.addValue("usr_phone", user.getPhone()).addValue("usr_goal", user.getGoal())
				.addValue("usr_description", user.getDescription());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(SQL.CREATE_USER, parameters, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public User readUserByUsername(String username) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("username", username);

		try {
			return this.jdbcTemplate.queryForObject(SQL.READ_USER_USERNAME, parameters, (rs, rowNum) -> {
				User user = new User();
				user.setId(rs.getLong("usr_id"));
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
				String goal = rs.getString("usr_goal");
				if (!rs.wasNull()) {
					user.setGoal(GoalEnum.valueOf(goal));
				}
				String sex = rs.getString("usr_sex");
				if (!rs.wasNull()) {
					user.setSex(SexEnum.valueOf(sex));
				}
				user.setDescription(rs.getString("usr_description"));

				return user;
			});
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}

	public boolean checkTrainerClient(String trainserUsername, String clientUsername) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("trainer_username", trainserUsername)
				.addValue("client_username", clientUsername).addValue("tc_request_status", "ACCEPTED");

		return this.jdbcTemplate.queryForObject(SQL.CHECK_TRAINER_CLIENT, parameters, (rs, rowNum) -> {
			return rowNum > 0 ? true : false;
		});

	}

	public List<SearchUser> readUsers(SearchData data) {

		List<SearchUser> result = new ArrayList<>();

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("username_flag", data.getUsername() != null ? 0 : 1)
				.addValue("usr_username", WILDCARD + data.getUsername() + WILDCARD)
				.addValue("first_name_flag", data.getFirstName() != null ? 0 : 1)
				.addValue("usr_firstname", WILDCARD + data.getFirstName() + WILDCARD)
				.addValue("last_name_flag", data.getLastName() != null ? 0 : 1)
				.addValue("usr_lastname", WILDCARD + data.getLastName() + WILDCARD)
				.addValue("from_age_flag", data.getFromAge() != null ? 0 : 1)
				.addValue("usr_from_age", data.getFromAge())
				.addValue("to_age_flag", data.getToAge() != null ? 0 : 1)
				.addValue("usr_to_age", data.getToAge())
				.addValue("is_trainer_flag", data.getIsTrainer() != null ? 0 : 1)
				.addValue("usr_is_trainer", data.getIsTrainer())
				.addValue("sex_flag", data.getSex() != null ? 0 : 1)
				.addValue("usr_sex", data.getSex());

		this.jdbcTemplate.query(SQL.READ_USERS, parameters, (rs, rowNum) -> {
			SearchUser user = new SearchUser();
			user.setUsername(rs.getString("usr_username"));
			user.setFirstName(rs.getString("usr_firstname"));
			user.setLastName(rs.getString("usr_lastname"));
			user.setIsTrainer(rs.getBoolean("usr_is_trainer"));

			result.add(user);

			return user;
		});
		
		return result;

	}
	
	public Long requestTrainer(Long trainerId, Long clientId) {
		
		DateFormat df = new SimpleDateFormat(this.dateFormat);
		Date dateobj = new Date();
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("tc_request_status", RequestStatusEnum.REQUESTED.getValue())
				.addValue("tc_request_sent_date", df.format(dateobj));
				
				
		KeyHolder keyHolder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(SQL.INSERT_TC_REQUEST, parameters, keyHolder);

		return keyHolder.getKey().longValue();
	}
	
	public String checkForExistingRequests(Long trainerId, Long clientId) {

		try {

			SqlParameterSource parameters = new MapSqlParameterSource().addValue("tc_trainer_id", trainerId)
					.addValue("tc_client_id", clientId).addValue("requested", RequestStatusEnum.REQUESTED.getValue())
					.addValue("accepted", RequestStatusEnum.ACCEPTED.getValue());

			return this.jdbcTemplate.queryForObject(SQL.CHECK_EXISTING_CLIENT_REQUESTS, parameters, (rs, rowNum) -> {

				return rs.getString("tc_request_status");
			});
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<SearchUser> readClientRequestUsers(String trainerUsername){
		
		List<SearchUser> result = new ArrayList<>();
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_trainer_username", trainerUsername)
				.addValue("requested", RequestStatusEnum.REQUESTED.getValue());
		
		this.jdbcTemplate.query(SQL.READ_CLIENT_REQUEST_USERS, parameters, (rs, rowNum) -> {
			SearchUser user = new SearchUser();
			user.setUsername(rs.getString("usr_username"));
			user.setFirstName(rs.getString("usr_firstname"));
			user.setLastName(rs.getString("usr_lastname"));
			user.setIsTrainer(rs.getBoolean("usr_is_trainer"));

			result.add(user);

			return user;
		});
		
		return result;
		
	}

	@Override
	public void acceptClientRequest(Long trainerId, Long clientId) {
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("tc_request_status", RequestStatusEnum.ACCEPTED.getValue());

		this.jdbcTemplate.update(SQL.UPDATE_TC_REQUEST_STATUS, parameters);
		
	}

	@Override
	public void rejectClientRequest(Long trainerId, Long clientId) {
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("tc_request_status", RequestStatusEnum.REJECTED.getValue());

		this.jdbcTemplate.update(SQL.UPDATE_TC_REQUEST_STATUS, parameters);
		
	}
	
	

}
