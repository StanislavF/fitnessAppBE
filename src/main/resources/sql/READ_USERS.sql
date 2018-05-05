SELECT * 
FROM `user` 
WHERE (:username_flag=1 OR usr_username=:usr_username)
  AND (:first_name_flag=1 OR usr_firstname=:usr_firstname)
  AND (:last_name_flag=1 OR usr_lastname=:usr_lastname)
  AND (:from_age_flag=1 OR usr_age>:usr_from_age)
  AND (:to_age_flag=1 OR usr_age<:usr_to_age)
  AND (:is_trainer_flag=1 OR usr_is_trainer=:usr_is_trainer)
  AND (:sex_flag=1 OR usr_sex=:usr_sex)