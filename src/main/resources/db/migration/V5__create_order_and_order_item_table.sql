CREATE TABLE `orders`
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_status varchar(32) NOT NULL
);

CREATE TABLE `order_items`
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity int NOT NULL
);