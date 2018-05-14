UPDATE `user` 
SET `usr_firstname`= :usr_firstname,
    `usr_lastname`= :usr_lastname,
    `usr_is_trainer`= :usr_is_trainer,
    `usr_sex`= :usr_sex,
    `usr_age`= :usr_age,
    `usr_weight`= :usr_weight,
    `usr_height`= :usr_height,
    `usr_phone`= :usr_phone,
    `usr_goal`= :usr_goal,
    `usr_description`= :usr_description,
    `usr_image`= :usr_image 
WHERE `usr_id` = :usr_id