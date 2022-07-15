DELIMITER //
CREATE TRIGGER `bucket_after_update_modified_on`
  AFTER UPDATE ON `bucket`
  FOR EACH ROW
BEGIN
  UPDATE `object`
    SET modified_on = CURRENT_TIMESTAMP
    WHERE object.id = NEW.id;
END//
DELIMITER ;
