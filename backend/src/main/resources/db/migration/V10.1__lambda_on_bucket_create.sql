CREATE TABLE `lambda_on_bucket` (
  `bucket_id` binary(16) NOT NULL,
  `lambda_name` VARCHAR(254) NOT NULL,
  PRIMARY KEY (`bucket_id`, `lambda_name`),
  CONSTRAINT `bucket_fk` FOREIGN KEY (`bucket_id`) REFERENCES `bucket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);