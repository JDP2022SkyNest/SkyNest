CREATE TABLE `bucket` (
  `id` binary(16) NOT NULL,
  `company_id` binary(16) NULL,
  `description` varchar(5000) NOT NULL,
  `size` bigint NOT NULL DEFAULT 0,
  `public` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_bucket_object_id`
    FOREIGN KEY (`id`)
    REFERENCES `object` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bucket_company_id`
    FOREIGN KEY (`company_id`)
    REFERENCES `company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
