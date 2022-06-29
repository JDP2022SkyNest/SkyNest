ALTER TABLE `company`
ADD COLUMN `pib` varchar(255) NOT NULL AFTER `deleted_on`,
ADD COLUMN `phone_number` varchar(30) NOT NULL AFTER `address`,
CHANGE COLUMN `domain` `email` varchar(254) NOT NULL,
ADD UNIQUE INDEX `pib_UNIQUE` (`pib` ASC) VISIBLE,
ADD UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE,
ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE;
