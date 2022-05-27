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
