USE tigerTalk;

DELETE FROM password_token WHERE id = 1;

DELETE FROM friendship_message WHERE message_id = 1;
DELETE FROM friendship WHERE friendship_id = 1;
DELETE FROM friendship_request WHERE friendship_request_id = 1;
DELETE FROM notification WHERE notification = 1;

DELETE FROM group_post_like WHERE id = 1;
DELETE FROM group_post_comment WHERE group_post_comment_id = 1;
DELETE FROM group_post WHERE group_post_id = 1;
DELETE FROM group_all WHERE group_id = 1;
DELETE FROM group_membership WHERE group_membership_id = 1;

DELETE FROM post_like WHERE id = 1;
DELETE FROM post_comment WHERE comment_id = 1;
DELETE FROM post WHERE post_id = 1;

DELETE FROM user_profile WHERE email = "a@dal.ca";

