CREATE KEYSPACE IF NOT EXISTS productsdb WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use productsdb;

CREATE TYPE if not exists price (value decimal, currency_code text);
CREATE TABLE if not exists Product(id bigint PRIMARY KEY, name varchar, current_price frozen<price> );



INSERT INTO Product (id, name, current_price) 
VALUES (13860428,'The Big Lebowski [Blu-ray]',{"value": 26.37 , "currency_code": 'USD'});


select JSON * from product;

CREATE TABLE IF NOT EXISTS sequence (name text, num bigint, PRIMARY KEY (name));
INSERT INTO sequence (name, num) VALUES ('product_sequence', 13860430);

select  * from product;