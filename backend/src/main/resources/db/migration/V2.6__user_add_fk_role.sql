ALTER TABLE `user`
ADD CONSTRAINT `fk_user_role_id`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
