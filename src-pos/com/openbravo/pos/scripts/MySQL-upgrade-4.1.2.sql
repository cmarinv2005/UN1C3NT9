--    uniCenta oPOS - Touch Friendly Point Of Sale
--    Copyright (c) 2009-2016 uniCenta
--    https://unicenta.com
--
--    This file is part of uniCenta oPOS.
--
--    uniCenta oPOS is free software: you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation, either version 3 of the License, or
--    (at your option) any later version.
--
--    uniCenta oPOS is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

-- Database upgrade script for MySQL
-- v4.1.2 - v4.1.3 3APR2016

--
-- CLEAR THE DECKS
--
DELETE FROM sharedtickets;

--
-- CREATE PRODUCT_BUNDLE
-- 
/* Header line. Object: products_bundle. Script date: 26/07/2016 15:25:00. */
CREATE TABLE IF NOT EXISTS `products_bundle` (
    `id` varchar(255) NOT NULL,
    `product` VARCHAR(255) NOT NULL,
    `product_bundle` VARCHAR(255) NOT NULL,
    `quantity` DOUBLE NOT NULL,
    PRIMARY KEY ( `id` ),
    UNIQUE INDEX `pbundle_inx_prod` ( `product` , `product_bundle` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

-- 
-- UPDATE ROLES
--
UPDATE `roles` SET `permissions` = $FILE{/com/openbravo/pos/templates/Role.Administrator.xml} WHERE `id` = '0';
UPDATE `roles` SET `permissions` = $FILE{/com/openbravo/pos/templates/Role.Manager.xml} WHERE `id` = '1';

-- UPDATE resources --
-- MENU
UPDATE `resources` SET `content` = $FILE{/com/openbravo/pos/templates/Menu.Root.txt} WHERE `id` = '0';

-- 
-- UPDATE PRODUCTS
--
ALTER TABLE `products` DROP INDEX `products_name_inx`;
ALTER TABLE `products` ADD INDEX `products_name_inx` (name);

/* Alter table in target */
ALTER TABLE `csvimport` 
	ADD COLUMN `tax` varchar(255)  COLLATE utf8_general_ci NULL after `category` ,
	ADD COLUMN `searchkey` varchar(255)  COLLATE utf8_general_ci NULL after `tax` ;

-- UPDATE App' version
UPDATE applications SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};