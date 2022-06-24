ALTER TABLE `company`
ADD CONSTRAINT `fk_company_tier_id`
  FOREIGN KEY (`tier_id`)
  REFERENCES `tier` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
