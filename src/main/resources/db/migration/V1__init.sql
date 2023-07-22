--
-- Table structure for table `airtime_products`
--

CREATE TABLE IF NOT EXISTS `airtime_products` (
  `id` bigint auto_increment PRIMARY KEY,
  `network_provider` varchar(50) NOT NULL,
  `product_code` varchar(50) NOT NULL,
  `min_amount` int NOT NULL,
  `max_amount` int NOT NULL,

    CONSTRAINT uk_network_provider UNIQUE (network_provider),
    CONSTRAINT uk_product_code UNIQUE (product_code),
    INDEX idx_network_provider (network_provider)
)
    ENGINE=InnoDB;