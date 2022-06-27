DELIMITER //
CREATE TRIGGER `file_after_delete_bucket_size`
  AFTER DELETE ON `file`
  FOR EACH ROW
BEGIN
  UPDATE `bucket`
    SET size = size - OLD.size
    WHERE bucket.id = OLD.bucket_id;
END//
DELIMITER ;
