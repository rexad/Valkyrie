DROP PROCEDURE IF EXISTS valkyrie_db.count_content;

DELIMITER $$
$$
CREATE PROCEDURE valkyrie_db.count_content()
BEGIN
	  SELECT COUNT(*) FROM Content;
END

$$
DELIMITER ;
