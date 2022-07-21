CREATE TABLE `tag` (
  `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `company_id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `rgb` varchar(6),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_tag_company_id`
      FOREIGN KEY (`company_id`)
      REFERENCES `company` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;