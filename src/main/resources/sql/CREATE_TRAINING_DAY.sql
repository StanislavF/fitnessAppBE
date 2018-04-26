INSERT INTO `training_day`( `td_id`, 
							`td_no`, 
							`td_title`, 
							`td_date`, 
							`td_usr_id`) 
SELECT  :td_id, 
		:td_no, 
		:td_title, 
		:td_date, 
		user.usr_id 
FROM user
WHERE user.usr_username=:usr_username