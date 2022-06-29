DELIMITER //
CREATE TRIGGER `folder_before_update_unique_name`
  BEFORE UPDATE ON `folder`
  FOR EACH ROW
BEGIN
  IF NOT (OLD.parent_folder_id<=>NEW.parent_folder_id AND OLD.bucket_id=NEW.bucket_id) THEN
    IF EXISTS(SELECT * FROM `folder` f JOIN `object` o ON (f.id=o.id) WHERE o.name=(SELECT o1.name FROM `object` o1 WHERE o1.id = NEW.id) AND f.parent_folder_id<=>NEW.parent_folder_id AND f.bucket_id=NEW.bucket_id) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Folder name must be unique within a folder';
    END IF;
  END IF;
END//
DELIMITER ;
