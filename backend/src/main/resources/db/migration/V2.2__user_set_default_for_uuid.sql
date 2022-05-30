ALTER TABLE `user`
MODIFY `id` binary(16) DEFAULT (uuid_to_bin(uuid()));
