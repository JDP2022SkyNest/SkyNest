--  modify old user entity
ALTER TABLE `user`
DROP COLUMN `user_id`,
ADD COLUMN `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `id`,
ADD COLUMN `modified_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `created_on`,
ADD COLUMN `deleted_on` datetime NULL DEFAULT NULL AFTER `modified_on`,
ADD COLUMN `address` varchar(255) NOT NULL AFTER `surname`,
ADD COLUMN `phone_number` varchar(30) NOT NULL AFTER `address`,
ADD COLUMN `verified` tinyint NOT NULL DEFAULT 0 AFTER `phone_number`,
ADD COLUMN `enabled` tinyint NOT NULL DEFAULT 0 AFTER `verified`,
CHANGE COLUMN `id` `id` binary(16) NOT NULL,
CHANGE COLUMN `email` `email` varchar(254) NOT NULL ,
CHANGE COLUMN `encrypted_password` `encrypted_password` varchar(60) NOT NULL ,
CHANGE COLUMN `name` `name` varchar(50) NOT NULL ,
CHANGE COLUMN `surname` `surname` varchar(100) NOT NULL ,
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
ADD UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE;

ALTER TABLE `user`
MODIFY `id` binary(16) DEFAULT (uuid_to_bin(uuid()));

--  add tables for roles and companies
CREATE TABLE `role` (
  `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `company` (
  `id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_on` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `domain` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--  foreign keys
ALTER TABLE `user`
ADD COLUMN `role_id` binary(16) NOT NULL AFTER `deleted_on`,
ADD COLUMN `company_id` binary(16) NULL AFTER `role_id`;

ALTER TABLE `user`
ADD CONSTRAINT `fk_user_role_id`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
ALTER TABLE `user`
ADD CONSTRAINT `fk_user_company_id`
  FOREIGN KEY (`company_id`)
  REFERENCES `company` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

--  init data
INSERT INTO `role` (`name`) VALUES ('role_worker');
INSERT INTO `role` (`name`) VALUES ('role_manager');
INSERT INTO `role` (`name`) VALUES ('role_admin');

INSERT INTO `company` (`name`, `address`, `domain`) VALUES ('HTEC group', 'Bulevar Milutina Milankovića 11b, Belgrade, Serbia', 'htecgroup.com');






