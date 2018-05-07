SELECT trainer.usr_username
FROM `trainer_client`
INNER JOIN `user` as trainer ON trainer_client.tc_trainer_id = trainer.usr_id
WHERE tc_client_id = :tc_client_id
  AND tc_request_status = :tc_request_status