CREATE TABLE `Media` (
	`id` int NOT NULL AUTO_INCREMENT,
	`category` varchar NOT NULL,
	`price` int NOT NULL,
	`quantity` int NOT NULL,
	`title` varchar NOT NULL,
	`value` int NOT NULL,
	`imageUrl` varchar NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `CD` (
	`it` int NOT NULL,
	`artist` varchar NOT NULL,
	`recordLabel` varchar NOT NULL,
	`musicType` varchar NOT NULL,
	`releaseDate` DATETIME
);

CREATE TABLE `Book` (
	`id` int NOT NULL,
	`author` varchar NOT NULL,
	`coverType` varchar NOT NULL,
	`publisher` varchar NOT NULL,
	`publishDate` DATETIME NOT NULL,
	`numOfPages` int NOT NULL,
	`languague` varchar NOT NULL,
	`bookCategory` varchar NOT NULL
);

CREATE TABLE `DVD` (
	`id` int NOT NULL,
	`discType` varchar NOT NULL,
	`director` varchar NOT NULL,
	`runtime` int NOT NULL,
	`studio` varchar NOT NULL,
	`subtitle` varchar NOT NULL,
	`releasedDate` DATE NOT NULL,
	`filmType` varchar NOT NULL
);

CREATE TABLE `DeliveryInfo` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar NOT NULL,
	`province` varchar NOT NULL,
	`instruction` varchar NOT NULL,
	`address` varchar NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Card` (
	`id` int NOT NULL AUTO_INCREMENT,
	`cardCode` varchar NOT NULL,
	`owner` varchar NOT NULL,
	`cvvCode` varchar NOT NULL,
	`dateExpired` varchar NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Order` (
	`id` int NOT NULL,
	`shippingFees` varchar NOT NULL,
	`deliveryInfoId` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `OrderMedia` (
	`mediaID` int NOT NULL,
	`orderID` int NOT NULL,
	`price` int NOT NULL,
	`quantity` int NOT NULL
);

CREATE TABLE `Invoice` (
	`id` int NOT NULL,
	`totalAmount` int NOT NULL,
	`orderId` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `PaymentTrancsaction` (
	`id` int NOT NULL,
	`createAt` DATE NOT NULL,
	`content` varchar NOT NULL,
	`method` varchar NOT NULL,
	`cardId` int NOT NULL,
	`invoiceId` int NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `CD` ADD CONSTRAINT `CD_fk0` FOREIGN KEY (`it`) REFERENCES `Media`(`id`);

ALTER TABLE `Book` ADD CONSTRAINT `Book_fk0` FOREIGN KEY (`id`) REFERENCES `Media`(`id`);

ALTER TABLE `DVD` ADD CONSTRAINT `DVD_fk0` FOREIGN KEY (`id`) REFERENCES `Media`(`id`);

ALTER TABLE `Order` ADD CONSTRAINT `Order_fk0` FOREIGN KEY (`deliveryInfoId`) REFERENCES `DeliveryInfo`(`id`);

ALTER TABLE `OrderMedia` ADD CONSTRAINT `OrderMedia_fk0` FOREIGN KEY (`mediaID`) REFERENCES `Media`(`id`);

ALTER TABLE `OrderMedia` ADD CONSTRAINT `OrderMedia_fk1` FOREIGN KEY (`orderID`) REFERENCES `Order`(`id`);

ALTER TABLE `Invoice` ADD CONSTRAINT `Invoice_fk0` FOREIGN KEY (`orderId`) REFERENCES `Order`(`id`);

ALTER TABLE `PaymentTrancsaction` ADD CONSTRAINT `PaymentTrancsaction_fk0` FOREIGN KEY (`cardId`) REFERENCES `Card`(`id`);

ALTER TABLE `PaymentTrancsaction` ADD CONSTRAINT `PaymentTrancsaction_fk1` FOREIGN KEY (`invoiceId`) REFERENCES `Invoice`(`id`);











