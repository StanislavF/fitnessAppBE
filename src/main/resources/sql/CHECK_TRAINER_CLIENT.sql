SELECT  trainer_client.*,
		trainers.usr_username as trainer_username,
        clients.usr_username as client_username
FROM `trainer_client`
INNER JOIN user trainers ON trainer_client.tc_trainer_id = trainers.usr_id
INNER JOIN user clients ON trainer_client.tc_client_id = clients.usr_id
WHERE trainers.usr_username = :trainer_username 
	AND clients.usr_username = :client_username 
	AND `tc_request_status` = :tc_request_status