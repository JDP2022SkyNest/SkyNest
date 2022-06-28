CREATE TABLE `action` (
  `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `performed_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` binary(16) NOT NULL,
  `action_type_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_action_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_action_action_type_id`
    FOREIGN KEY (`action_type_id`)
    REFERENCES `action_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
