DELIMITER //
CREATE TRIGGER `folder_before_insert_unique_name`
  BEFORE INSERT ON `folder`
  FOR EACH ROW
BEGIN
  IF EXISTS(SELECT * FROM `folder` f JOIN `object` o ON (f.id=o.id) WHERE o.name=(SELECT o1.name FROM `object` o1 WHERE o1.id = NEW.id) AND f.parent_folder_id<=>NEW.parent_folder_id AND f.bucket_id=NEW.bucket_id) THEN
    DELETE FROM `object` WHERE `object`.id = NEW.id;
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Folder name must be unique within a folder';
  END IF;
END//
DELIMITER ;
