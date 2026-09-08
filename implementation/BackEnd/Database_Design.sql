SET FOREIGN_KEY_CHECKS = 0;
SET UNIQUE_CHECKS = 0;

DROP TABLE IF EXISTS `users` CASCADE;
CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `uid` varchar(255),
  `username` varchar(255),
  `password_hash` varchar(255),
  `email` varchar(255) NOT NULL UNIQUE,
  `created_at` timestamp,
  `bio` varchar(255),
  `phone_number` varchar(255),
  `birth_date` date,
  `gender` varchar(255),
  `visibility` boolean,
  `avatar_id` int
);

DROP TABLE IF EXISTS `user_positions` CASCADE;
CREATE TABLE `user_positions` (
  `user_id` int,
  `lat` float,
  `lng` float
);

DROP TABLE IF EXISTS `user_addresses` CASCADE;
CREATE TABLE `user_addresses` (
  `user_id` int,
  `street` varchar(255),
  `house_number` int,
  `zip_code` int,
  `city` varchar(255),
  `country` varchar(255)
);

DROP TABLE IF EXISTS `user_socials` CASCADE;
CREATE TABLE `user_socials` (
  `user_id` int,
  `social_id` int,
  `handle` varchar(255), /* constrain, validate later */
  PRIMARY KEY(`user_id`, `social_id`)
);

DROP TABLE IF EXISTS `socials` CASCADE;
CREATE TABLE `socials` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

DROP TABLE IF EXISTS `relationships` CASCADE;
CREATE TABLE `relationships` (
  `user_from_id` int,
  `user_to_id` int,
  `type` int, /*can be a whole new table {REQUESTED, ANSWERED, BLOCKED, etc...} */
  `created_at` timestamp
);

DROP TABLE IF EXISTS `interests` CASCADE;
CREATE TABLE `interests` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `type` int,
  `name` varchar(255),
  `short_desc` varchar(255)
);

DROP TABLE IF EXISTS `interest_types` CASCADE;
CREATE TABLE `interest_types` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255)
);

DROP TABLE IF EXISTS `user_interests` CASCADE;
CREATE TABLE `user_interests` (
  `user_id` int,
  `interest_id` int,
  `desc` varchar(255),
  `start_date` timestamp,
  `proficiency` int,
  PRIMARY KEY(`user_id`, `interest_id`)
);

DROP TABLE IF EXISTS `images` CASCADE;
CREATE TABLE `images` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `owner_id` int,
  `visibility` int,
  `name` varchar(255),
  `title` varchar(255),
  `desc` varchar(255),
  `created_at` timestamp
);

ALTER TABLE `user_interests` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_interests` ADD FOREIGN KEY (`interest_id`) REFERENCES `interests` (`id`);

ALTER TABLE `images` ADD FOREIGN KEY (`owner_id`) REFERENCES `users` (`id`);

ALTER TABLE `relationships` ADD FOREIGN KEY (`user_from_id`) REFERENCES `users` (`id`);

ALTER TABLE `relationships` ADD FOREIGN KEY (`user_to_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_socials` ADD FOREIGN KEY (`social_id`) REFERENCES `socials` (`id`);

ALTER TABLE `user_socials` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `user_addresses` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`avatar_id`) REFERENCES `images` (`id`);

ALTER TABLE `interests` ADD FOREIGN KEY (`type`) REFERENCES `interest_types` (`id`);

ALTER TABLE `user_positions` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

SET FOREIGN_KEY_CHECKS = 1;
SET UNIQUE_CHECKS = 1;