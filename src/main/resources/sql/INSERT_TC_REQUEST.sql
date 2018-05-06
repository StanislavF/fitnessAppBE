INSERT INTO `trainer_client`(`tc_trainer_id`, 
							 `tc_client_id`, 
							 `tc_request_status`, 
							 `tc_request_sent_date`) 
VALUES (:tc_trainer_id, 
		:tc_client_id, 
		:tc_request_status, 
		:tc_request_sent_date)