ALTER TABLE `action`
ADD COLUMN `revoked_by` BINARY(16) NULL DEFAULT NULL AFTER `action_type_id`;
