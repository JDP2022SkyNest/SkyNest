CREATE TABLE `folder` (
  `id` binary(16) NOT NULL,
  `parent_folder_id` binary(16) NULL DEFAULT NULL,
  `bucket_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_folder_object_id`
    FOREIGN KEY (`id`)
    REFERENCES `object` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_folder_parent_folder_id`
    FOREIGN KEY (`parent_folder_id`)
    REFERENCES `folder` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_folder_bucket_id`
    FOREIGN KEY (`bucket_id`)
    REFERENCES `bucket` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
