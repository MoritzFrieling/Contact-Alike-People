# DATE WIPE
/*
delete from `users`;
delete from `user_positions`;
delete from `user_addresses`;
delete from `user_socials`;
delete from `socials`;
delete from `relationships`;
delete from `interests`;
delete from `interest_types`;
delete from `user_interests`;
delete from `images`;
*/

alter table `socials` auto_increment = 1;
insert into `socials`(`name`)
values ('Discord'),
       ('Snapchat'),
       ('Steam'),
       ('Origin'),
       ('Epic Games'),
       ('Playstation Network'),
       ('Xbox Live'),
       ('Skype'),
       ('Instagram'),
       ('Facebook');

alter table `interest_types` auto_increment = 1;
insert into `interest_types`(`name`)
values ('Gaming'),
       ('Activity'),
       ('Language');

alter table `interests` auto_increment = 1;
insert into `interests`(`type`, `name`, `short_desc`)
values (1, 'League of Legends', '-'),
       (1, 'Destiny 2', '-'),
       (1, 'Rocket League', '-'),
       (1, 'Valorant', '-'),
       (1, 'Counter Strike: Global Offensive', '-'),
       (1, 'Elden Ring', '-'),
       (1, 'Hollow Knight', '-'),
       (1, 'Skyrim', '-'),
       (1, 'Grand Theft Auto V', '-'),
       (1, 'Lego Star Wars: Die Skywalker Saga', '-'),
       (2, 'Fußball', '-'),
       (2, 'Badminton', '-'),
       (2, 'Tischtennis', '-'),
       (3, 'Deutsch', '-'),
       (3, 'English', '-'),
       (3, 'Dutch', '-');

alter table `users` auto_increment = 1;
insert into `users`(`uid`, `username`, `password_hash`, `email`, `created_at`, `bio`, `phone_number`, `birth_date`, `gender`, `visibility`, `avatar_id`)
values ('12345', 'Jonas', 'j0n_vh', '114086785aa113161784eba51ee3044b776e3feb', 'jonas31720@gmail.com', '2020-06-04 11:20:01', 'studying...', '017647361249', '2000-07-31', 'Male', 'true', '42069'),
       ('42123', 'Philip', 'phi1ip_K', '1c873dfa58af1dd78638f419c252cd7fc23445b1', 'philip.kuentges@aol.com', '2020-06-04 11:21:01', 'sleeping...', '015259802268', '2001-07-30', 'Male', 'true', '37183');

alter table `user_positions` auto_increment = 1;
insert into `user_positions`(`lat`, 'lng')
values ('null', 'null');

alter table `user_addresses` auto_increment = 1;
insert into `user_addresses`(`street`, `house_number`, `zip_code`, `city`, `country`)
values ('Honnenpfad', '6', '47249', 'Duisburg', 'Deutschland');

alter table `user_socials` auto_increment = 1;
insert into `user_socials`(`social_id`, `handle`)
values ('1', 'text');

alter table `relationships`;
insert into `relationships`(`user_from_id`, `user_to_id`, `type`, `created_at`)
values ('1', '2', 'ANSWERED', '2020-06-04 11:20:01');

alter table `interest_types`;
insert into `interest_types`(`id`, `name`)
values ('1', 'interest'),
       ('2', 'hobby'),
       ('3', 'language');

alter table `user_interests` auto_increment = 1;
insert into `user_interests`(`interest_id`, `desc`, `start_date`, `proficiency`)
values ('2', '-', '2014-05-04 11:20:01', 'PRO');

alter table `images` auto_increment = 1;
insert into `images`(`owner_id`, `visibility`, `name`, `title`, `desc`, `created_at`)
values ('1', 'true', 'Jonas', 'Me', '2020-05-04 11:21:01');