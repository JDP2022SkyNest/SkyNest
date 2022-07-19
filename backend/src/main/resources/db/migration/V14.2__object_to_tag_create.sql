CREATE TABLE `object_to_tag` (
  `tag_id` binary(16) NOT NULL,
  `object_id` binary(16) NOT NULL,
  PRIMARY KEY (`tag_id`,`object_id`),
  CONSTRAINT `fk_object_to_tag_tag_id`
      FOREIGN KEY (`tag_id`)
      REFERENCES `tag` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  CONSTRAINT `fk_object_to_tag_object_id`
      FOREIGN KEY (`object_id`)
      REFERENCES `object` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;