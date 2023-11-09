
create database AIMS;
use AIMS;
create table invoice (
	id int not null AUTO_INCREMENT,
    totalAmount int not null,
    payment_transaction_id int not null,
    order_id int  not null,
    primary key (id)
);
create table payment_transaction (
	id int not null AUTO_INCREMENT,
    amount int not null,
    method nvarchar(50) ,
    created_at datetime,
    content nvarchar(1000),
    card_id int not null,
    primary key(id)
);
create table card (
	id int not null AUTO_INCREMENT,
    card_code varchar(20) not null,
    owner nvarchar(50) not null,
    cv_code varchar(20)  not null,
    date_expired date not null,
    primary key (id)
);
create table order_table (
	id int not null AUTO_INCREMENT,
    shipping_fee int not null,
    delivery_infor_id int not null,
    primary key (id)
);
create table delivery_infor (
	id int not null AUTO_INCREMENT,
    province nvarchar(100) not null,
    name nvarchar(50) not null,
    address nvarchar(200)  not null,
    instruction nvarchar(200),
    primary key (id)
);
create table media (
	id int not null AUTO_INCREMENT,
    category nvarchar(100) not null,
    price int not null,
    quantity int  not null,
    title nvarchar(200) not null,
    image_url varbinary(1000),
	order_id int,
    primary key (id)
);
create table cd (
	id int not null AUTO_INCREMENT,
    music_type varchar(100) not null,
    record_lable nvarchar(100) not null,
    artist nvarchar(50)  not null,
    release_date date,
    media_id int not null,
    primary key (id)
);
create table dvd (
	id int not null AUTO_INCREMENT,
    disc_type varchar(100) not null,
    director nvarchar(50) not null,
    runtime date,
    studio nvarchar(200),
    subtitle varchar(200),
    release_date date,
    media_id int not null,
    primary key (id)
);
create table book (
	id int not null AUTO_INCREMENT,
    book_category varchar(100) not null,
    author nvarchar(50) not null,
    cover_type varchar(100),
    publisher varchar(100),
    subtitle varchar(200),
    publish_date date,
    number_of_page int,
    language varchar(100),
    media_id int not null,
    primary key (id)
);
-- set foreign key 
ALTER TABLE payment_transaction
ADD FOREIGN KEY (card_id) REFERENCES card(id); 
ALTER TABLE invoice
ADD FOREIGN KEY (payment_transaction_id) REFERENCES payment_transaction(id); 
ALTER TABLE invoice
ADD FOREIGN KEY (order_id) REFERENCES order_table(id); 
ALTER TABLE order_table
ADD FOREIGN KEY (delivery_infor_id) REFERENCES delivery_infor(id); 
ALTER TABLE media
ADD FOREIGN KEY (order_id) REFERENCES order_table(id); 
ALTER TABLE dvd
ADD FOREIGN KEY (media_id) REFERENCES media(id); 
ALTER TABLE cd
ADD FOREIGN KEY (media_id) REFERENCES media(id); 
ALTER TABLE book
ADD FOREIGN KEY (media_id) REFERENCES media(id); 

-- update 
ALTER TABLE payment_transaction
MODIFY COLUMN method varchar(50) CHARACTER SET utf8mb4;
ALTER TABLE card
MODIFY COLUMN owner varchar(50) CHARACTER SET utf8mb4;
ALTER TABLE invoice
MODIFY COLUMN id int not null AUTO_INCREMENT;
ALTER TABLE card
MODIFY COLUMN id int not null AUTO_INCREMENT;
-- insert
INSERT INTO card (card_code, owner, cv_code, date_expired)
VALUES ("123", "phan đức thịnh", "456",  '2002-01-01');

-- index
CREATE INDEX invoice_index ON invoice (totalAmount);
CREATE INDEX payment_index ON payment_transaction (amount, method);
CREATE INDEX order_table_index ON order_table (shipping_fee);
CREATE INDEX delivery_infor_index ON delivery_infor (province);
CREATE INDEX media ON media (quantity, title);


