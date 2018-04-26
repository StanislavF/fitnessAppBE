INSERT INTO `exercise_row`( `er_no`, 
							`er_ex_id`, 
							`er_sets`, 
							`er_reps`, 
							`er_weight`, 
							`er_comment`, 
							`er_td_id`) 
SELECT  :er_no, 
		exercise.ex_id , 
		:er_sets, 
		:er_reps, 
		:er_weight, 
		:er_comment, 
		:er_td_id
FROM exercise
WHERE exercise.ex_name = :ex_name