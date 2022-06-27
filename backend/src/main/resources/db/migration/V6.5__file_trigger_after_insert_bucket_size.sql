DELIMITER //
CREATE TRIGGER `file_after_insert_bucket_size`
  AFTER INSERT ON `file`
  FOR EACH ROW
BEGIN
  UPDATE `bucket`
    SET size = size + NEW.size
    WHERE bucket.id = NEW.bucket_id;
END//
DELIMITER ;
