
create DATABASE `booksstoreDB`;

USE `booksstoreDB`;

/*DROP TABLE IF EXISTS `products`;*/

CREATE TABLE `products` (
  `productID` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `category` varchar(30) NOT NULL,
  `quantity` int(4) NOT NULL,
  `description` varchar(300),
  `price` decimal(8,2) NOT NULL,
  `shippingDays` int(3) NOT NULL,
  `rating` decimal(3,2) NOT NULL,
  `totalrating` int(10) NOT NULL,
  PRIMARY KEY (`productID`)
);

/*Data for the table `products` */

INSERT  INTO `products`(`productID`,`name`,`category`,`quantity`,`description`,`price`,`shippingDays`,`rating`,`totalrating`) VALUES

(10,'Puzzle Mickey Mouse','Board Games', 1,'For kids +3 years','30.99',2,'4.30',0),

(11,'Porcelain set with cups','Tea and accessories',2,'Included 4 tea cups and one pack of tea','99.99',2,'4.00',0),

(12,'Painting with kittens','Home and deco',3,'100% handmade','55.50', 4,'4.80',0),

(13,'When Life Gets Harder','Book',4,'Full life of work from home person.','20.99',2,'5.00',0);


/*Table structure for table `user` */

/*DROP TABLE IF EXISTS `user`;*/

create TABLE `users` (
  `userID` int(10) AUTO_INCREMENT NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `userType` int(10) NOT NULL,
  `fidelityPoints` int(15) NOT NULL,
  `address` varchar(100) NOT NULL,
  `cart` varchar(200) NOT NULL,
  `phoneNumber` varchar(10) NOT NULL,
	
  PRIMARY KEY (`userID`)

);


/*Data for the table `users` */

insert  into `users`(`userID`,`lastName`,`firstName`,`password`,`email`,`balance`,`userType`,`fidelityPoints`,`address`,`cart`,`phoneNumber`) values

(20,'Ion','Maria','456XZXZ_AA','maria@booksstore.com','100',0,0,'Bucuresti, Sector 2','','+400756123456'),

(21,'Vlas','Andrei','999*asdfQA','vandrei@booksstore.com','200',0,0,'Cluj Napoca, Str. Unirii, 12','','+400757896123');

/*Table structure for table `orders` */

/*DROP TABLE IF EXISTS `orders`; */

create TABLE `orders` (
  `oderID` int(10) AUTO_INCREMENT NOT NULL,
  `userID` int(10) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `orderDate` date NOT NULL,
  `shippingDate` date NOT NULL,
  `orderProducts` varchar(200) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
 
  PRIMARY KEY (`orderID`),
  FOREIGN KEY (`userID`) REFERENCES `users` (`userID`)
);

--Data for the table `orders` */

/*insert into `orders` (`orderID`,`userID`,`price`,`orderDate`,`shippingDate`,`orderProducts`,`status`) values  */




/*Table structure for table `books` */

/* DROP TABLE IF EXISTS `books`; */

CREATE TABLE `books` (
  `id` int(10) NOT NULL,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(20) NOT NULL,
  `pages` int(5) NOT NULL,
  `category` varchar(50) NOT NULL,
  `publishedYear` date NOT NULL,
  `languages` varchar(10) NOT NULL,
  
  PRIMARY KEY (`id`)

); 

/*Data for the table `books` */

insert  into `books`(`id`,`title`,`author`,`publisher`,`pages`,`category`,`publishedYear`,`languages`) values

(30,'When Life Gets Harder','Yuval Noah Harari','Rainbow',220, 'Drama','2020-03-21','english'),

(31,'Clubul lui Peppa Pig','Peppa Pig','Litera',40,'Kids' ,'2019-08-27','romanian');



/*Table structure for table `discounts` */

/* DROP TABLE IF EXISTS `discounts`; */

create TABLE `discounts`(
  `discountCode` varchar(20) NOT NULL,
  `discountType` int(1) NOT NULL,
  `amount` decimal(6,2) NOT NULL

  PRIMARY KEY (`discountCode`)

);
insert  into `discounts`(`discountCode`,`discountType`,`amount`,`category`) values

('WOWYOUROOM',2,'19,99,'Drama'),
('WEEKEND',2,'15.89','Kida');



