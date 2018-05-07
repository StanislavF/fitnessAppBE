UPDATE `trainer_client` 
SET `tc_request_status`= :tc_request_status
WHERE `tc_trainer_id` = :tc_trainer_id
  AND `tc_client_id` = :tc_client_id 