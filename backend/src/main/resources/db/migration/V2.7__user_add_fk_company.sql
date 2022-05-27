ALTER TABLE `user`
ADD CONSTRAINT `fk_user_company_id`
  FOREIGN KEY (`company_id`)
  REFERENCES `company` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
