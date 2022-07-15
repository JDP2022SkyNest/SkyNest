DELIMITER //
CREATE TRIGGER `file_after_update_modified_on`
  AFTER UPDATE ON `file`
  FOR EACH ROW
BEGIN
  UPDATE `object`
    SET modified_on = CURRENT_TIMESTAMP
    WHERE object.id = NEW.id;
END//
DELIMITER ;
