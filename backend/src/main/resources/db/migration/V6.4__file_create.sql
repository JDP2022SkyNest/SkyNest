CREATE TABLE `file` (
  `id` BINARY(16) NOT NULL,
  `parent_folder_id` BINARY(16) NULL DEFAULT NULL,
  `bucket_id` BINARY(16) NOT NULL,
  `type` VARCHAR(255) NOT NULL,
  `size` BIGINT NOT NULL DEFAULT 0,
  `content_id` VARCHAR(24) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `size_chk_non_negative`
    CHECK (`size` >= 0),
  CONSTRAINT `fk_file_object_id`
    FOREIGN KEY (`id`)
    REFERENCES `object` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_file_parent_folder_id`
    FOREIGN KEY (`parent_folder_id`)
    REFERENCES `folder` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_file_bucket_id`
    FOREIGN KEY (`bucket_id`)
    REFERENCES `bucket` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
