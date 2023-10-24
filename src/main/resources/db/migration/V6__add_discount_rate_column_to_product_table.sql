ALTER TABLE `product`
ADD COLUMN discount_rate float NOT NULL DEFAULT 0;


UPDATE `product`
SET discount_rate = 0.8
WHERE id = 1;

UPDATE `product`
SET discount_rate = 0.85
WHERE id = 2;