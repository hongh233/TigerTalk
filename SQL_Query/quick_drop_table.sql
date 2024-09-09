USE tigerTalk;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS password_token;

DROP TABLE IF EXISTS friendship_message;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS friendship_request;
DROP TABLE IF EXISTS notification;

DROP TABLE IF EXISTS group_post_like;
DROP TABLE IF EXISTS group_post_comment;
DROP TABLE IF EXISTS group_post;
DROP TABLE IF EXISTS group_all;
DROP TABLE IF EXISTS group_membership;

DROP TABLE IF EXISTS post_like;
DROP TABLE IF EXISTS post_comment;
DROP TABLE IF EXISTS post;

DROP TABLE IF EXISTS user_profile;
SET foreign_key_checks = 1;