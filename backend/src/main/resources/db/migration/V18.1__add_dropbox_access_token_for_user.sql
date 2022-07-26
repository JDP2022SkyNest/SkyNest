ALTER TABLE `user`
ADD COLUMN `dropbox_access_token` varchar(500) NULL AFTER `enabled`;