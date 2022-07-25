INSERT INTO `user` (`role_id`, `company_id`, `email`, `encrypted_password`, `name`, `surname`, `address`, `phone_number`, `verified`, `enabled`)
VALUES (
(SELECT `id` from `role` WHERE `name`='role_admin'),
(SELECT `id` from `company` WHERE `pib`='105793138'),
'skynest12345@gmail.com',
 '$2a$12$jfhlmwS9XpfHvfhvH6Sa.OzK98F4lK4e3.tyLYOgiJnNsZ5szBbJy',
 'Admin',
 'Adminovski',
 'Milutina Milankovica 11b',
 '0615558899',
 true,
 true
);