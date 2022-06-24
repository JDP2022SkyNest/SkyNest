DELIMITER //
CREATE TRIGGER `file_after_update_bucket_size`
  AFTER UPDATE ON `file`
  FOR EACH ROW
BEGIN
  UPDATE `bucket`
    SET size = size + (NEW.size - OLD.size)
    WHERE bucket.id = NEW.bucket_id;
END//
DELIMITER ;
