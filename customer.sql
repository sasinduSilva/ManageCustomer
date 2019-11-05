
CREATE TABLE `customer` (
  `id` varchar(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);
COMMIT;


