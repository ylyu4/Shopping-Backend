ALTER TABLE `product`
    ADD COLUMN stock int NOT NULL DEFAULT 0;


UPDATE `product`
SET stock = 100
WHERE id = 1;

UPDATE `product`
SET stock = 80
WHERE id = 2;
