DELIMITER //
CREATE PROCEDURE `change_user_role` (IN user_email CHAR(254), IN new_role CHAR(255))
BEGIN
    DECLARE updateCount INT DEFAULT 0;

    UPDATE user
    SET role_id = (SELECT id FROM role WHERE name = new_role)
    WHERE email = user_email;

    SET updateCount = (SELECT ROW_COUNT());
    CASE updateCount
            WHEN 1 THEN SELECT concat('changed ', user_email, ' to ', new_role) AS 'OPERATION RESULT';
            ELSE SELECT 'no changes' AS 'OPERATION RESULT';
    END CASE;
END//
DELIMITER ;