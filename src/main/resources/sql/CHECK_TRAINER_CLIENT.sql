SELECT  *
FROM `trainer_client`
INNER JOIN user clients ON trainer_client.tc_client_id = clients.usr_id
WHERE tc_trainer_id = :trainer_id 
	AND clients.usr_username = :client_username 
	AND `tc_request_status` IN (:tc_request_status_accepted, 
								:tc_request_status_canceled)