ALTER TABLE `action`
ADD CONSTRAINT `fk_action_revoked_by`
  FOREIGN KEY (`revoked_by`)
  REFERENCES `action` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
