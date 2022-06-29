DELIMITER //
CREATE TRIGGER `object_before_update_unique_folder_name`
  BEFORE UPDATE ON `object`
  FOR EACH ROW
BEGIN
  IF NOT (OLD.name=NEW.name) THEN
    IF EXISTS(SELECT * FROM `object` o JOIN `folder` f ON (o.id=f.id) WHERE o.name=NEW.name AND f.parent_folder_id<=>(SELECT f1.parent_folder_id FROM `folder` f1 WHERE f1.id=NEW.id) AND f.bucket_id=(SELECT f2.bucket_id FROM `folder` f2 WHERE f2.id=NEW.id)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Folder name must be unique within a folder';
    END IF;
  END IF;
END//
DELIMITER ;
