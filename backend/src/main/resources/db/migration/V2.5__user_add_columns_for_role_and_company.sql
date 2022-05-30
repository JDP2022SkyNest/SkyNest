ALTER TABLE `user`
ADD COLUMN `role_id` binary(16) NOT NULL AFTER `deleted_on`,
ADD COLUMN `company_id` binary(16) NULL AFTER `role_id`;
