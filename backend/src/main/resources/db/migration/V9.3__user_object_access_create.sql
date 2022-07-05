CREATE TABLE `user_object_access` (
  `granted_to` binary(16) NOT NULL,
  `object_id` binary(16) NOT NULL,
  `granted_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `access_id` binary(16) NOT NULL,
  `granted_by` binary(16) NOT NULL,
  PRIMARY KEY (`granted_to`,`object_id`),
  CONSTRAINT `fk_user_object_access_granted_to`
    FOREIGN KEY (`granted_to`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_object_access_object_id`
    FOREIGN KEY (`object_id`)
    REFERENCES `object` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_object_access_access_id`
    FOREIGN KEY (`access_id`)
    REFERENCES `access` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_object_access_granted_by`
    FOREIGN KEY (`granted_by`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
