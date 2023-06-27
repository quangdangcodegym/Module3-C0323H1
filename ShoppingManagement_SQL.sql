CREATE DATABASE shopping_management;

CREATE TABLE `products` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `price` DOUBLE NULL,
  `description` VARCHAR(45) NULL,
  `deleted` DATETIME NULL,
  `category_id` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `category` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `deleted` DATETIME NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `products` 
ADD INDEX `fk_categoryid_idx` (`category_id` ASC) VISIBLE;
;
ALTER TABLE `products` 
ADD CONSTRAINT `fk_categoryid`
  FOREIGN KEY (`category_id`)
  REFERENCES `category` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total` DOUBLE NULL,
  `created_at` DATETIME NULL,
  `fullname` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `orderitems` (
  `id` INT NOT NULL,
  `id_order` INT NULL,
  `id_product` INT NULL,
  `price` DOUBLE NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_idorder_idx` (`id_order` ASC) VISIBLE,
  INDEX `fk_idproduct_idx` (`id_product` ASC) VISIBLE,
  CONSTRAINT `fk_idorder`
    FOREIGN KEY (`id_order`)
    REFERENCES `orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_idproduct`
    FOREIGN KEY (`id_product`)
    REFERENCES `products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);