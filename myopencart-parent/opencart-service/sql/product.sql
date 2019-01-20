CREATE TABLE `product` (
  `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `detail` text,
  `price` int(11) NOT NULL,
  `vat` int(11) DEFAULT NULL,
  `brand` varchar(32) NOT NULL,
  `point` int(11) DEFAULT NULL,
  `picture_main_url` varchar(200) DEFAULT NULL,
  `picture_urls` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_code` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8