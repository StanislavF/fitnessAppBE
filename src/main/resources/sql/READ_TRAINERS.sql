SELECT trainer.usr_username
FROM `trainer_client`
INNER JOIN `user` as trainer ON trainer_client.tc_trainer_id = trainer.usr_id
WHERE tc_client_id = :tc_client_id
  AND tc_request_status IN (:tc_request_status_accepted, :tc_request_status_canceled)
  AND tc_is_visible_for_client = 1