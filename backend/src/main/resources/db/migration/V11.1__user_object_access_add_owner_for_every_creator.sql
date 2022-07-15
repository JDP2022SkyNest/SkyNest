INSERT INTO `user_object_access`
(`granted_to`,
`object_id`,
`granted_on`,
`access_id`,
`granted_by`)
SELECT `object`.`created_by`, `object`.`id`, `object`.`created_on`, `access`.`id`, `object`.`created_by`
FROM
    `object`
        JOIN
    `access` ON (`access`.`name` = 'owner')
WHERE `object`.`id` NOT IN (
    SELECT `user_object_access`.`object_id`
    FROM
        `user_object_access`
            JOIN
        `object` o1 ON (o1.`id`=`user_object_access`.`object_id`)
    );
