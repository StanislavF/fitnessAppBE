SELECT client.usr_username
FROM `trainer_client`
INNER JOIN `user` as client ON trainer_client.tc_client_id = client.usr_id
WHERE tc_trainer_id=:tc_trainer_id 
  AND tc_request_status IN (:tc_request_status_accepted, :tc_request_status_canceled)
  AND tc_is_visible_for_trainer = 1