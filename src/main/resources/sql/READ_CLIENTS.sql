SELECT client.usr_username
FROM `trainer_client`
INNER JOIN `user` as client ON trainer_client.tc_client_id = client.usr_id
WHERE tc_trainer_id=:tc_trainer_id 
  AND tc_request_status = :tc_request_status