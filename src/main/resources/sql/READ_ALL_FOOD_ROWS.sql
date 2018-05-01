SELECT * 
FROM `food_row` 
INNER JOIN `food` ON food_row.fr_fd_id = food.fd_id
WHERE fr_sm_id = :fr_sm_id