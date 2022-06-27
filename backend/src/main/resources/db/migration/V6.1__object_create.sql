CREATE TABLE `object` (
  `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_on` datetime NULL DEFAULT NULL,
  `created_by` binary(16) NOT NULL,
  `name` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_object_created_by_user_id`
    FOREIGN KEY (`created_by`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
