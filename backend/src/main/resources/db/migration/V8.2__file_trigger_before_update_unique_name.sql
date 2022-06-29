DELIMITER //
CREATE TRIGGER `file_before_update_unique_name`
  BEFORE UPDATE ON `file`
  FOR EACH ROW
BEGIN
  IF NOT (OLD.parent_folder_id<=>NEW.parent_folder_id AND OLD.bucket_id=NEW.bucket_id) THEN
    IF EXISTS(SELECT * FROM `file` f JOIN `object` o ON (f.id=o.id) WHERE o.name=(SELECT o1.name FROM `object` o1 WHERE o1.id = NEW.id) AND f.parent_folder_id<=>NEW.parent_folder_id AND f.bucket_id=NEW.bucket_id) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'File name must be unique within a folder';
    END IF;
  END IF;
END//
DELIMITER ;
