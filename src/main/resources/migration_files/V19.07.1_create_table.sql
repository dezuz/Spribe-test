CREATE TABLE article (
    id BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(255),
    posted_date VARCHAR(255),
    name VARCHAR(255),
    content VARCHAR,
    section_id BIGINT
);

CREATE TABLE section (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

ALTER TABLE article
    ADD CONSTRAINT fk_article_id
        FOREIGN KEY(section_id)REFERENCES section(id);

