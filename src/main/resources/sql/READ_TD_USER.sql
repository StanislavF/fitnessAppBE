SELECT * 
FROM `training_day` 
WHERE td_client_id = :td_client_id
	AND td_trainer_id = :td_trainer_id
	AND training_day.td_date = :td_date