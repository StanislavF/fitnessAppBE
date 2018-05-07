SELECT * 
FROM `trainer_client`
WHERE tc_trainer_id=:tc_trainer_id 
  AND tc_client_id=:tc_client_id
  AND tc_request_status in (:requested, :accepted)