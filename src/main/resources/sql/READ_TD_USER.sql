SELECT * 
FROM `training_day` 
INNER JOIN user ON training_day.td_usr_id = user.usr_id
WHERE user.usr_username = :usr_username 
	AND training_day.td_date = :td_date