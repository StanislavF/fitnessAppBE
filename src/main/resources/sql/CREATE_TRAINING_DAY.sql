INSERT INTO `training_day`( `td_id`, 
							`td_no`, 
							`td_title`, 
							`td_date`, 
							`td_client_id`,
							`td_trainer_id`) 
VALUES (:td_id, 
		:td_no, 
		:td_title, 
		:td_date, 
		:td_client_id,
		:td_trainer_id)
