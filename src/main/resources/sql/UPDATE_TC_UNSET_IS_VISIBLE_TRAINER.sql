UPDATE `trainer_client` 
SET `tc_is_visible_for_trainer`= :tc_is_visible_trainer
WHERE `tc_trainer_id` = :tc_trainer_id
  AND `tc_client_id` = :tc_client_id 
  AND `tc_request_status` = :tc_request_status