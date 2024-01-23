DELIMITER //
CREATE TRIGGER add_user_forum
AFTER INSERT ON user
FOR EACH ROW
BEGIN
    INSERT INTO user_forums (forumid, userid) VALUES (1, NEW.userid);
END;
//
DELIMITER ;