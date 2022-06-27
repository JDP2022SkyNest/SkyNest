UPDATE `company` SET `tier_id` = (SELECT `id` FROM `tier` WHERE `name` = 'basic') WHERE `name` = 'HTEC group';
