ALTER TABLE `company`
ADD COLUMN `tier_id` binary(16) NOT NULL AFTER `deleted_on`;