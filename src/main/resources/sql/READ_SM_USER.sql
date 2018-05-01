SELECT * 
FROM `single_meal` 
WHERE sm_client_id = :sm_client_id,
	AND sm_trainer_id = :sm_trainer_id,
	AND single_meal.sm_date = :sm_date