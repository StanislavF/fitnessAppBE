SELECT  client.usr_username, 
		client.usr_firstname,
        client.usr_lastname,
        client.usr_is_trainer
FROM `trainer_client` 
	INNER JOIN `user` as trainer ON trainer_client.tc_trainer_id = trainer.usr_id 
    INNER JOIN `user` as client ON trainer_client.tc_client_id = client.usr_id
WHERE trainer.usr_username = :usr_trainer_username
  AND trainer_client.tc_request_status=:requested