BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Book" (
	"id"	INTEGER NOT NULL,
	"author"	VARCHAR(45) NOT NULL,
	"coverType"	VARCHAR(45) NOT NULL,
	"publisher"	VARCHAR(45) NOT NULL,
	"publishDate"	DATETIME NOT NULL,
	"numOfPages"	INTEGER NOT NULL,
	"language"	VARCHAR(45) NOT NULL,
	"bookCategory"	VARCHAR(45) NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	CONSTRAINT "fk_book_media" FOREIGN KEY("id") REFERENCES "Media"("id")
);
CREATE TABLE IF NOT EXISTS "CD" (
	"id"	INTEGER NOT NULL,
	"artist"	VARCHAR(45) NOT NULL,
	"recordLabel"	VARCHAR(45) NOT NULL,
	"musicType"	VARCHAR(45) NOT NULL,
	"releasedDate"	DATE,
	PRIMARY KEY("id"),
	CONSTRAINT "fk_cd_media" FOREIGN KEY("id") REFERENCES "Media"("id")
);
CREATE TABLE IF NOT EXISTS "Card" (
	"id"	INTEGER NOT NULL,
	"cardNumber"	VARCHAR(45) NOT NULL,
	"holderName"	VARCHAR(45) NOT NULL,
	"expirationDate"	DATE NOT NULL,
	"securityCode"	VARCHAR(45) NOT NULL,
	"userID"	INTEGER NOT NULL,
	PRIMARY KEY("id"),
	CONSTRAINT "fk_card_user" FOREIGN KEY("userID") REFERENCES "User"("id")
);
CREATE TABLE IF NOT EXISTS "DVD" (
	"id"	INTEGER NOT NULL,
	"discType"	VARCHAR(45) NOT NULL,
	"director"	VARCHAR(45) NOT NULL,
	"runtime"	INTEGER NOT NULL,
	"studio"	VARCHAR(45) NOT NULL,
	"subtitle"	VARCHAR(45) NOT NULL,
	"releasedDate"	DATETIME,
	"filmType"	VARCHAR(45) NOT NULL,
	PRIMARY KEY("id"),
	CONSTRAINT "fk_dvd_media" FOREIGN KEY("id") REFERENCES "Media"("id")
);
CREATE TABLE IF NOT EXISTS "Media" (
	"id"	INTEGER NOT NULL,
	"type"	VARCHAR(45) NOT NULL,
	"category"	VARCHAR(45) NOT NULL,
	"price"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	"title"	VARCHAR(45) NOT NULL,
	"value"	INTEGER NOT NULL,
	"imageUrl"	VARCHAR(45) NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "Order" (
	"id"	INTEGER NOT NULL,
	"email"	VARCHAR(45) NOT NULL,
	"address"	VARCHAR(45) NOT NULL,
	"phone"	VARCHAR(45) NOT NULL,
	"userID"	INTEGER NOT NULL,
	"shipping_fee"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	CONSTRAINT "fk_order_user" FOREIGN KEY("userID") REFERENCES "User"("id")
);
CREATE TABLE IF NOT EXISTS "OrderMedia" (
	"mediaID"	INTEGER NOT NULL,
	"orderID"	INTEGER NOT NULL,
	"price"	INTEGER NOT NULL,
	"quantity"	INTEGER NOT NULL,
	PRIMARY KEY("mediaID","orderID"),
	CONSTRAINT "fk_ordermedia_media" FOREIGN KEY("mediaID") REFERENCES "Media"("id"),
	CONSTRAINT "fk_ordermedia_order" FOREIGN KEY("orderID") REFERENCES "Order"("id")
);
CREATE TABLE IF NOT EXISTS "Transaction" (
	"id"	INTEGER NOT NULL,
	"orderID"	INTEGER NOT NULL,
	"createAt"	DATETIME NOT NULL,
	"content"	VARCHAR(45) NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	CONSTRAINT "fk_transaction_order" FOREIGN KEY("orderID") REFERENCES "Order"("id")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER NOT NULL,
	"name"	VARCHAR(45) NOT NULL,
	"email"	VARCHAR(45) NOT NULL,
	"address"	VARCHAR(45) NOT NULL,
	"phone"	VARCHAR(45) NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
CREATE INDEX IF NOT EXISTS "Card.fk_card_user_idx" ON "Card" (
	"userID"
);
CREATE INDEX IF NOT EXISTS "Order.fk_order_user_idx" ON "Order" (
	"userID"
);
CREATE INDEX IF NOT EXISTS "OrderMedia.fk_ordermedia_order_idx" ON "OrderMedia" (
	"orderID"
);
CREATE INDEX IF NOT EXISTS "Transaction.fk_transaction_order_idx" ON "Transaction" (
	"orderID"
);
COMMIT;
