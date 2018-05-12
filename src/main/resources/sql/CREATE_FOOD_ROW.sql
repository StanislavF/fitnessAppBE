INSERT INTO `food_row`( 	`fr_no`, 
							`fr_fd_id`, 
							`fr_weight`, 
							`fr_calories`, 
							`fr_proteins`, 
							`fr_carbs`,
							`fr_fats`,
							`fr_sm_id`) 
SELECT  :fr_no, 
		food.fd_id , 
		:fr_weight, 
		:fr_calories, 
		:fr_proteins, 
		:fr_carbs, 
		:fr_fats,
		:fr_sm_id
FROM food
WHERE food.fd_name = :fd_name