SELECT * 
FROM `exercise_row` 
INNER JOIN `exercise` ON exercise_row.er_ex_id = exercise.ex_id
WHERE er_td_id = :er_td_id