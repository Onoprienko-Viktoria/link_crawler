# link_crawler

This program works with docker and postgresql

docker run --name store-postgres -p 5433:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=webstore -d postgres


CREATE TABLE domains
(
    name character varying(120)  NOT NULL,
    all_links numeric NOT NULL,
    internal_links numeric NOT NULL,
    external_links numeric NOT NULL,
    CONSTRAINT domens_pkey PRIMARY KEY (name)
)


CREATE TABLE links
 (
    domain character varying(100) NOT NULL,
    url character varying(120) NOT NULL,
    nesting_level numeric NOT NULL,
    external_links numeric NOT NULL,
    CONSTRAINT links_pkey PRIMARY KEY (url)
 )



