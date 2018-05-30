package da.proj.fitnessApp.repositrory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Blob;
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
import org.springframework.web.multipart.MultipartFile;

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

	private String WILDCARD = "%";

	public Long createUser(User user) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("usr_username", user.getUsername())
				.addValue("usr_firstname", user.getFirstName()).addValue("usr_lastname", user.getLastName())
				.addValue("usr_email", user.getEmail()).addValue("usr_is_trainer", user.getIsTrainer())
				.addValue("usr_password", user.getPassword()).addValue("usr_age", user.getAge())
				.addValue("usr_weight", user.getWeight()).addValue("usr_height", user.getHeight())
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
				user.setWeight(rs.getInt("usr_weight"));
				user.setHeight(rs.getInt("usr_height"));
				user.setPhone(rs.getString("usr_phone"));
				user.setGoal(rs.getString("usr_goal"));
				user.setSex(rs.getString("usr_sex"));
				
				user.setDescription(rs.getString("usr_description"));

				return user;
			});
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

	}

	public String checkTrainerClient(Long trainerId, String clientUsername) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("trainer_id", String.valueOf(trainerId))
				.addValue("client_username", clientUsername)
				.addValue("tc_request_status_accepted", RequestStatusEnum.ACCEPTED.getValue())
				.addValue("tc_request_status_canceled", RequestStatusEnum.CANCELED.getValue());

		try {
			return this.jdbcTemplate.queryForObject(SQL.CHECK_TRAINER_CLIENT, parameters, (rs, rowNum) -> {
				return rs.getString("tc_request_status");
			});
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}

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
				.addValue("usr_from_age", data.getFromAge()).addValue("to_age_flag", data.getToAge() != null ? 0 : 1)
				.addValue("usr_to_age", data.getToAge())
				.addValue("is_trainer_flag", data.getIsTrainer() != null ? 0 : 1)
				.addValue("usr_is_trainer", data.getIsTrainer()).addValue("sex_flag", data.getSex() != null ? 0 : 1)
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

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("tc_trainer_id", trainerId)
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

	public List<SearchUser> readClientRequestUsers(String trainerUsername) {

		List<SearchUser> result = new ArrayList<>();

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("usr_trainer_username", trainerUsername)
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
				.addValue("new_tc_request_status", RequestStatusEnum.ACCEPTED.getValue())
				.addValue("old_tc_request_status", RequestStatusEnum.REQUESTED.getValue());

		this.jdbcTemplate.update(SQL.UPDATE_TC_REQUEST_STATUS, parameters);

	}

	@Override
	public void rejectClientRequest(Long trainerId, Long clientId) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("new_tc_request_status", RequestStatusEnum.REJECTED.getValue())
				.addValue("old_tc_request_status", RequestStatusEnum.REQUESTED.getValue());

		this.jdbcTemplate.update(SQL.UPDATE_TC_REQUEST_STATUS, parameters);

	}

	@Override
	public List<String> readClientsUsername(Long trainerId) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("tc_trainer_id", trainerId)
				.addValue("tc_request_status_accepted", RequestStatusEnum.ACCEPTED.getValue())
				.addValue("tc_request_status_canceled", RequestStatusEnum.CANCELED.getValue());

		return this.jdbcTemplate.queryForList(SQL.READ_CLIENTS, parameters, String.class);
	}

	@Override
	public List<String> readTrainersUsername(Long clientId) {

		SqlParameterSource parameters = new MapSqlParameterSource().addValue("tc_client_id", clientId)
				.addValue("tc_request_status_accepted", RequestStatusEnum.ACCEPTED.getValue())
				.addValue("tc_request_status_canceled", RequestStatusEnum.CANCELED.getValue());

		return this.jdbcTemplate.queryForList(SQL.READ_TRAINERS, parameters, String.class);
	}

	@Override
	public void cancelTrainerClient(Long clientId, Long trainerId) {

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("new_tc_request_status", RequestStatusEnum.CANCELED.getValue())
				.addValue("old_tc_request_status", RequestStatusEnum.ACCEPTED.getValue());

		this.jdbcTemplate.update(SQL.UPDATE_TC_REQUEST_STATUS, parameters);

	}

	@Override
	public void removeClientFromTrainerVisability(Long clientId, Long trainerId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("tc_request_status", RequestStatusEnum.CANCELED.getValue())
				.addValue("tc_is_visible_trainer", 0);

		this.jdbcTemplate.update(SQL.UPDATE_TC_UNSET_IS_VISIBLE_TRAINER, parameters);
		
	}

	@Override
	public void removeTrainerFromClientVisability(Long clientId, Long trainerId) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("tc_trainer_id", trainerId)
				.addValue("tc_client_id", clientId)
				.addValue("tc_request_status", RequestStatusEnum.CANCELED.getValue())
				.addValue("tc_is_visible_client", 0);

		this.jdbcTemplate.update(SQL.UPDATE_TC_UNSET_IS_VISIBLE_CLIENT, parameters);
		
	}

	@Override
	public void updateUser(User user) {

		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_firstname", user.getFirstName())
				.addValue("usr_lastname", user.getLastName())
				.addValue("usr_is_trainer", user.getIsTrainer())
				.addValue("usr_age", user.getAge())
				.addValue("usr_sex",  user.getSex())
				.addValue("usr_weight", user.getWeight())
				.addValue("usr_height", user.getHeight())
				.addValue("usr_phone", user.getPhone())
				.addValue("usr_goal", user.getGoal())
				.addValue("usr_description", user.getDescription())
				.addValue("usr_id", user.getId());


		this.jdbcTemplate.update(SQL.UPDATE_USER, parameters);

		
	}

	@Override
	public void updatePassword(String username, String password) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_username", username)
				.addValue("usr_password", password);
		
		this.jdbcTemplate.update(SQL.UPDATE_PASSWORD, parameters);
		
	}

	@Override
	public void updateEmail(String username, String email) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_username", username)
				.addValue("usr_email", email);
		
		this.jdbcTemplate.update(SQL.UPDATE_EMAIL, parameters);
	}

	@Override
	public InputStream readUserImage(String username) {
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("usr_username", username);

		try {
			return this.jdbcTemplate.queryForObject(SQL.READ_IMAGE, parameters, (rs, rownum) -> {
				Blob blob = rs.getBlob("usr_image");
				
				return blob != null ?  blob.getBinaryStream() : null;
			});
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public void updateImage(User user, InputStream image) {
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("usr_image", image)
				.addValue("usr_id", user.getId());
		
		this.jdbcTemplate.update(SQL.UPDATE_IMAGE, parameters);
		
	}

}
