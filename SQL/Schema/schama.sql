CREATE TABLE valkyrie_db.Content (
	id BIGINT NOT NULL,
	text_content TEXT(100) NOT NULL,
	CONSTRAINT Content_PK PRIMARY KEY (id),
	CONSTRAINT Content_Source_FK FOREIGN KEY (id) REFERENCES valkyrie_db.`Source`(id),
	CONSTRAINT Content_Language_FK FOREIGN KEY (id) REFERENCES valkyrie_db.`Language`(id)
) ;
