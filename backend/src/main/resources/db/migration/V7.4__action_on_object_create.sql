CREATE TABLE `action_on_object` (
  `action_id` binary(16) NOT NULL,
  `object_id` binary(16) NOT NULL,
  PRIMARY KEY (`action_id`,`object_id`),
  CONSTRAINT `fk_action_on_object_action_id`
    FOREIGN KEY (`action_id`)
    REFERENCES `action` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_action_on_object_object_id`
    FOREIGN KEY (`object_id`)
    REFERENCES `object` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
