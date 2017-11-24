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

/*
 * Script created by Jack, uniCenta 27/08/2015 08:42:37.
 *
 * Creating for version unicentaopos42.
*/


/* Header line. Object: applications. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `applications` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`version` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attribute. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attribute` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeinstance. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attributeinstance` (
	`id` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`value` varchar(255) default NULL,
	KEY `attinst_att` ( `attribute_id` ),
	KEY `attinst_set` ( `attributesetinstance_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeset. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attributeset` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributesetinstance. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attributesetinstance` (
	`id` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) NOT NULL,
	`description` varchar(255) default NULL,
	KEY `attsetinst_set` ( `attributeset_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributeuse. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attributeuse` (
	`id` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`lineno` int(11) default NULL,
	KEY `attuse_att` ( `attribute_id` ),
	UNIQUE INDEX `attuse_line` ( `attributeset_id`, `lineno` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: attributevalue. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `attributevalue` (
	`id` varchar(255) NOT NULL,
	`attribute_id` varchar(255) NOT NULL,
	`value` varchar(255) default NULL,
	KEY `attval_att` ( `attribute_id` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: breaks. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `breaks` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`visible` tinyint(1) NOT NULL default '1',
	`notes` varchar(255) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: categories. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `categories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`parentid` varchar(255) default NULL,
	`image` mediumblob default NULL,
	`texttip` varchar(255) default NULL,
	`catshowname` smallint(6) NOT NULL default '1',
	`catorder` varchar(255) default NULL,
	KEY `categories_fk_1` ( `parentid` ),
	UNIQUE INDEX `categories_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: closedcash. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `closedcash` (
	`money` varchar(255) NOT NULL,
	`host` varchar(255) NOT NULL,
	`hostsequence` int(11) NOT NULL,
	`datestart` datetime NOT NULL,
	`dateend` datetime default NULL,
	`nosales` int(11) NOT NULL default '0',
	KEY `closedcash_inx_1` ( `datestart` ),
	UNIQUE INDEX `closedcash_inx_seq` ( `host`, `hostsequence` ),
	PRIMARY KEY  ( `money` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: csvimport. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `csvimport` (
	`id` varchar(255) NOT NULL,
	`rownumber` varchar(255) default NULL,
	`csverror` varchar(255) default NULL,
	`reference` varchar(255) default NULL,
	`code` varchar(255) default NULL,
	`name` varchar(255) default NULL,
	`pricebuy` double default NULL,
	`pricesell` double default NULL,
	`previousbuy` double default NULL,
	`previoussell` double default NULL,
	`category` varchar(255) default NULL,
	`tax` varchar(255) default NULL,
	`searchkey` varchar(255) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: customers. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `customers` (
	`id` varchar(255) NOT NULL,
	`searchkey` varchar(255) NOT NULL,
	`taxid` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`taxcategory` varchar(255) default NULL,
	`card` varchar(255) default NULL,
	`maxdebt` double NOT NULL default '0',
	`address` varchar(255) default NULL,
	`address2` varchar(255) default NULL,
	`postal` varchar(255) default NULL,
	`city` varchar(255) default NULL,
	`region` varchar(255) default NULL,
	`country` varchar(255) default NULL,
	`firstname` varchar(255) default NULL,
	`lastname` varchar(255) default NULL,
	`email` varchar(255) default NULL,
	`phone` varchar(255) default NULL,
	`phone2` varchar(255) default NULL,
	`fax` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	`visible` bit(1) NOT NULL default b'1',
	`curdate` datetime default NULL,
	`curdebt` double default '0',
	`image` mediumblob default NULL,
	`isvip` bit(1) NOT NULL default b'0',
	`discount` double default '0',
	KEY `customers_card_inx` ( `card` ),
	KEY `customers_name_inx` ( `name` ),
	UNIQUE INDEX `customers_skey_inx` ( `searchkey` ),
	KEY `customers_taxcat` ( `taxcategory` ),
	KEY `customers_taxid_inx` ( `taxid` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

CREATE TABLE `dbpermissions` (
  `CLASSNAME` varchar(255) NOT NULL,
  `SECTION` varchar(255) NOT NULL,
  `DISPLAYNAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255) NOT NULL,
  `SITEGUID` varchar(50) NOT NULL DEFAULT '97708bbd-cd5b-40f3-bbc5-852e364d8ac2',
  `SFLAG` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: draweropened. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `draweropened` (
	`opendate` timestamp NOT NULL,
	`name` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: floors. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `floors` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`image` mediumblob default NULL,
	UNIQUE INDEX `floors_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: leaves. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `leaves` (
	`id` varchar(255) NOT NULL,
	`pplid` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`startdate` datetime NOT NULL,
	`enddate` datetime NOT NULL,
	`notes` varchar(255) default NULL,
	KEY `leaves_pplid` ( `pplid` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: lineremoved. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `lineremoved` (
	`removeddate` timestamp NOT NULL,
	`name` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL,
	`productid` varchar(255) default NULL,
	`productname` varchar(255) default NULL,
	`units` double NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: locations. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `locations` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`address` varchar(255) default NULL,
	UNIQUE INDEX `locations_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: moorers. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `moorers` (
	`vesselname` varchar(255) default NULL,
	`size` int(11) default NULL,
	`days` int(11) default NULL,
	`power` bit(1) NOT NULL default b'0'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: payments. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `payments` (
	`id` varchar(255) NOT NULL,
	`receipt` varchar(255) NOT NULL,
	`payment` varchar(255) NOT NULL,
	`total` double NOT NULL default '0',
	`tip` double default '0',
	`transid` varchar(255) default NULL,
	`isprocessed` bit(1) default b'0',
	`returnmsg` mediumblob default NULL,
	`notes` varchar(255) default NULL,
	`tendered` double default NULL,
	`cardname` varchar(255) default NULL,
        `voucher` varchar(255) default NULL,
	KEY `payments_fk_receipt` ( `receipt` ),
	KEY `payments_inx_1` ( `payment` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: people. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `people` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`apppassword` varchar(255) default NULL,
	`card` varchar(255) default NULL,
	`role` varchar(255) NOT NULL,
	`visible` bit(1) NOT NULL,
	`image` mediumblob default NULL,
	KEY `people_card_inx` ( `card` ),
	KEY `people_fk_1` ( `role` ),
	UNIQUE INDEX `people_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: pickup_number. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `pickup_number` (
	`id` int(11) NOT NULL default '0'
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: places. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `places` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`x` int(11) NOT NULL,
	`y` int(11) NOT NULL,
	`floor` varchar(255) NOT NULL,
	`customer` varchar(255) default NULL,
	`waiter` varchar(255) default NULL,
	`ticketid` varchar(255) default NULL,
	`tablemoved` smallint(6) NOT NULL default '0',
	UNIQUE INDEX `places_name_inx` ( `name` ),
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products. Script date: 02/04/2016 10:53:00. */
CREATE TABLE `products` (
	`id` varchar(255) NOT NULL,
	`reference` varchar(255) NOT NULL,
	`code` varchar(255) NOT NULL,
	`codetype` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`pricebuy` double NOT NULL default '0',
	`pricesell` double NOT NULL default '0',
	`category` varchar(255) NOT NULL,
	`taxcat` varchar(255) NOT NULL,
	`attributeset_id` varchar(255) default NULL,
	`stockcost` double NOT NULL default '0',
	`stockvolume` double NOT NULL default '0',
	`image` mediumblob default NULL,
	`iscom` bit(1) NOT NULL default b'0',
	`isscale` bit(1) NOT NULL default b'0',
	`isconstant` bit(1) NOT NULL default b'0',
	`printkb` bit(1) NOT NULL default b'0',
	`sendstatus` bit(1) NOT NULL default b'0',
	`isservice` bit(1) NOT NULL default b'0',
	`attributes` mediumblob default NULL,
	`display` varchar(255) default NULL,
	`isvprice` smallint(6) NOT NULL default '0',
	`isverpatrib` smallint(6) NOT NULL default '0',
	`texttip` varchar(255) default NULL,
	`warranty` smallint(6) NOT NULL default '0',
	`stockunits` double NOT NULL default '0',
	`printto` varchar(255) default '1',
        `warning` timestamp NULL,
        `expiry` timestamp NULL,
	`supplier` varchar(255) default NULL,
        `uom` varchar(255) default '0',

	PRIMARY KEY  ( `id` ),
	KEY `products_attrset_fx` ( `attributeset_id` ),
	KEY `products_fk_1` ( `category` ),
	UNIQUE INDEX `products_inx_0` ( `reference` ),
	UNIQUE INDEX `products_inx_1` ( `code` ),
	INDEX `products_name_inx` ( `name` ),
	KEY `products_taxcat_fk` ( `taxcat` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_cat. Script date: 11/05/2016 05:25:00. */
CREATE TABLE `products_bundle` (
    `id` varchar(255) NOT NULL,
    `product` VARCHAR(255) NOT NULL,
    `product_bundle` VARCHAR(255) NOT NULL,
    `quantity` DOUBLE NOT NULL,
    PRIMARY KEY ( `id` ),
    UNIQUE INDEX `pbundle_inx_prod` ( `product` , `product_bundle` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_cat. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `products_cat` (
	`product` varchar(255) NOT NULL,
	`catorder` int(11) default NULL,
	PRIMARY KEY  ( `product` ),
	KEY `products_cat_inx_1` ( `catorder` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: products_com. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `products_com` (
	`id` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`product2` varchar(255) NOT NULL,
	UNIQUE INDEX `pcom_inx_prod` ( `product`, `product2` ),
	PRIMARY KEY  ( `id` ),
	KEY `products_com_fk_2` ( `product2` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: receipts. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `receipts` (
	`id` varchar(255) NOT NULL,
	`money` varchar(255) NOT NULL,
	`datenew` datetime NOT NULL,
	`attributes` mediumblob default NULL,
	`person` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `receipts_fk_money` ( `money` ),
	KEY `receipts_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: reservation_customers. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `reservation_customers` (
	`id` varchar(255) NOT NULL,
	`customer` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	KEY `res_cust_fk_2` ( `customer` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: reservations. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `reservations` (
	`id` varchar(255) NOT NULL,
	`created` datetime NOT NULL,
	`datenew` datetime NOT NULL default '2015-01-01 00:00:00',
	`title` varchar(255) NOT NULL,
	`chairs` int(11) NOT NULL,
	`isdone` bit(1) NOT NULL,
	`description` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `reservations_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: resources. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `resources` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`restype` int(11) NOT NULL,
	`content` mediumblob default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `resources_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: roles. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `roles` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`permissions` mediumblob default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `roles_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: sharedtickets. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `sharedtickets` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`content` mediumblob default NULL,
	`appuser` varchar(255) default NULL,
	`pickupid` smallint(6) NOT NULL default '0',
	`locked` varchar(20) default NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: shift_breaks. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `shift_breaks` (
	`id` varchar(255) NOT NULL,
	`shiftid` varchar(255) NOT NULL,
	`breakid` varchar(255) NOT NULL,	
	`starttime` datetime NOT NULL,
	`endtime` datetime default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `shift_breaks_breakid` ( `breakid` ),
	KEY `shift_breaks_shiftid` ( `shiftid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: shifts. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `shifts` (
	`id` varchar(255) NOT NULL,
	`startshift` datetime NOT NULL,
	`endshift` datetime default NULL,
	`pplid` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stockcurrent. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `stockcurrent` (
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	KEY `stockcurrent_attsetinst` ( `attributesetinstance_id` ),
	KEY `stockcurrent_fk_1` ( `product` ),
	UNIQUE INDEX `stockcurrent_inx` ( `location`, `product`, `attributesetinstance_id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stockdiary. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `stockdiary` (
	`id` varchar(255) NOT NULL,
	`datenew` datetime NOT NULL,
	`reason` int(11) NOT NULL,
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	`price` double NOT NULL,
	`appuser` varchar(255) default NULL,
	`supplier` varchar(255) default NULL,
	`supplierdoc` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `stockdiary_attsetinst` ( `attributesetinstance_id` ),
	KEY `stockdiary_fk_1` ( `product` ),
	KEY `stockdiary_fk_2` ( `location` ),
	KEY `stockdiary_inx_1` ( `datenew` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: stocklevel. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `stocklevel` (
	`id` varchar(255) NOT NULL,
	`location` varchar(255) NOT NULL,
	`product` varchar(255) NOT NULL,
	`stocksecurity` double default NULL,
	`stockmaximum` double default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `stocklevel_location` ( `location` ),
	KEY `stocklevel_product` ( `product` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: suppliers. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `suppliers` (
	`id` varchar(255) NOT NULL,
	`searchkey` varchar(255) NOT NULL,
	`taxid` varchar(255) default NULL,
	`name` varchar(255) NOT NULL,
	`maxdebt` double NOT NULL default '0',
	`address` varchar(255) default NULL,
	`address2` varchar(255) default NULL,
	`postal` varchar(255) default NULL,
	`city` varchar(255) default NULL,
	`region` varchar(255) default NULL,
	`country` varchar(255) default NULL,
	`firstname` varchar(255) default NULL,
	`lastname` varchar(255) default NULL,
	`email` varchar(255) default NULL,
	`phone` varchar(255) default NULL,
	`phone2` varchar(255) default NULL,
	`fax` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	`visible` bit(1) NOT NULL default b'1',
	`curdate` datetime default NULL,
	`curdebt` double default '0',
	`vatid` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `suppliers_name_inx` ( `name` ),
	UNIQUE INDEX `suppliers_skey_inx` ( `searchkey` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxcategories. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `taxcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `taxcat_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxcustcategories. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `taxcustcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `taxcustcat_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxes. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `taxes` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`category` varchar(255) NOT NULL,
	`custcategory` varchar(255) default NULL,
	`parentid` varchar(255) default NULL,
	`rate` double NOT NULL default '0',
	`ratecascade` bit(1) NOT NULL default b'0',
	`rateorder` int(11) default NULL,
	PRIMARY KEY  ( `id` ),
	KEY `taxes_cat_fk` ( `category` ),
	KEY `taxes_custcat_fk` ( `custcategory` ),
	UNIQUE INDEX `taxes_name_inx` ( `name` ),
	KEY `taxes_taxes_fk` ( `parentid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxlines. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `taxlines` (
	`id` varchar(255) NOT NULL,
	`receipt` varchar(255) NOT NULL,
	`taxid` varchar(255) NOT NULL,
	`base` double NOT NULL default '0',
	`amount` double NOT NULL default '0',
	PRIMARY KEY  ( `id` ),
	KEY `taxlines_receipt` ( `receipt` ),
	KEY `taxlines_tax` ( `taxid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: taxsuppcategories. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `taxsuppcategories` (
	`id` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: thirdparties. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `thirdparties` (
	`id` varchar(255) NOT NULL,
	`cif` varchar(255) NOT NULL,
	`name` varchar(255) NOT NULL,
	`address` varchar(255) default NULL,
	`contactcomm` varchar(255) default NULL,
	`contactfact` varchar(255) default NULL,
	`payrule` varchar(255) default NULL,
	`faxnumber` varchar(255) default NULL,
	`phonenumber` varchar(255) default NULL,
	`mobilenumber` varchar(255) default NULL,
	`email` varchar(255) default NULL,
	`webpage` varchar(255) default NULL,
	`notes` varchar(255) default NULL,
	PRIMARY KEY  ( `id` ),
	UNIQUE INDEX `thirdparties_cif_inx` ( `cif` ),
	UNIQUE INDEX `thirdparties_name_inx` ( `name` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketlines. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `ticketlines` (
	`ticket` varchar(255) NOT NULL,
	`line` int(11) NOT NULL,
	`product` varchar(255) default NULL,
	`attributesetinstance_id` varchar(255) default NULL,
	`units` double NOT NULL,
	`price` double NOT NULL,
	`taxid` varchar(255) NOT NULL,
	`attributes` mediumblob default NULL,
	PRIMARY KEY  ( `ticket`, `line` ),
	KEY `ticketlines_attsetinst` ( `attributesetinstance_id` ),
	KEY `ticketlines_fk_2` ( `product` ),
	KEY `ticketlines_fk_3` ( `taxid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: tickets. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `tickets` (
	`id` varchar(255) NOT NULL,
	`tickettype` int(11) NOT NULL default '0',
	`ticketid` int(11) NOT NULL,
	`person` varchar(255) NOT NULL,
	`customer` varchar(255) default NULL,
	`status` int(11) NOT NULL default '0',
	PRIMARY KEY  ( `id` ),
	KEY `tickets_customers_fk` ( `customer` ),
	KEY `tickets_fk_2` ( `person` ),
	KEY `tickets_ticketid` ( `tickettype`, `ticketid` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `ticketsnum` (
	`id` int(11) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum_payment. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `ticketsnum_payment` (
--	`id` int(11) NOT NULL auto_increment,
	`id` int(11) NOT NULL,
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: ticketsnum_refund. Script date: 27/08/2015 08:42:37. */
CREATE TABLE `ticketsnum_refund` (
	`id` int(11) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: uom. Script date: 30/09/2015 13:07:00. */
CREATE TABLE `uom` (
    `id` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

/* Header line. Object: vouchers. Script date: 30/09/2015 09:33:33. */
CREATE TABLE `vouchers` (
   `id` VARCHAR(100) NOT NULL,
   `voucher_number` VARCHAR(100) DEFAULT NULL,
   `customer` VARCHAR(100) DEFAULT NULL,
   `amount` DOUBLE DEFAULT NULL,
   `status` CHAR(1) DEFAULT 'A',
  PRIMARY KEY ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;

-- Update foreign keys of attributeinstance
ALTER TABLE `attributeinstance` ADD CONSTRAINT `attinst_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` );

ALTER TABLE `attributeinstance` ADD CONSTRAINT `attinst_set`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributesetinstance
ALTER TABLE `attributesetinstance` ADD CONSTRAINT `attsetinst_set`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributeuse
ALTER TABLE `attributeuse` ADD CONSTRAINT `attuse_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` );

ALTER TABLE `attributeuse` ADD CONSTRAINT `attuse_set`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of attributevalue
ALTER TABLE `attributevalue` ADD CONSTRAINT `attval_att`
	FOREIGN KEY ( `attribute_id` ) REFERENCES `attribute` ( `id` ) ON DELETE CASCADE;

-- Update foreign keys of categories
ALTER TABLE `categories` ADD CONSTRAINT `CATEGORIES_FK_1`
	FOREIGN KEY ( `parentid` ) REFERENCES `categories` ( `id` );

-- Update foreign keys of customers
ALTER TABLE `customers` ADD CONSTRAINT `customers_taxcat`
	FOREIGN KEY ( `taxcategory` ) REFERENCES `taxcustcategories` ( `id` );

-- Update foreign keys of leaves
ALTER TABLE `leaves` ADD CONSTRAINT `leaves_pplid`
	FOREIGN KEY ( `pplid` ) REFERENCES `people` ( `id` );

-- Update foreign keys of payments
ALTER TABLE `payments` ADD CONSTRAINT `payments_fk_receipt`
	FOREIGN KEY ( `receipt` ) REFERENCES `receipts` ( `id` );

-- Update foreign keys of people
ALTER TABLE `people` ADD CONSTRAINT `people_fk_1`
	FOREIGN KEY ( `role` ) REFERENCES `roles` ( `id` );

-- Update foreign keys of products
ALTER TABLE `products` ADD CONSTRAINT `products_attrset_fk`
	FOREIGN KEY ( `attributeset_id` ) REFERENCES `attributeset` ( `id` );

ALTER TABLE `products` ADD CONSTRAINT `products_fk_1`
	FOREIGN KEY ( `category` ) REFERENCES `categories` ( `id` );

ALTER TABLE `products` ADD CONSTRAINT `products_taxcat_fk`
	FOREIGN KEY ( `taxcat` ) REFERENCES `taxcategories` ( `id` );

-- Update foreign keys of product_bundle
ALTER TABLE `products_bundle` ADD CONSTRAINT `products_bundle_fk_1` 
        FOREIGN KEY ( `product` ) REFERENCES `products`( `id` );

ALTER TABLE `products_bundle` ADD CONSTRAINT `products_bundle_fk_2`     
        FOREIGN KEY ( `product_bundle` ) REFERENCES `products`( `id` );

-- Update foreign keys of products_cat
ALTER TABLE `products_cat` ADD CONSTRAINT `products_cat_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

-- Update foreign keys of products_com
ALTER TABLE `products_com` ADD CONSTRAINT `products_com_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `products_com` ADD CONSTRAINT `products_com_fk_2`
	FOREIGN KEY ( `product2` ) REFERENCES `products` ( `id` );

-- Update foreign keys of receipts
ALTER TABLE `receipts` ADD CONSTRAINT `receipts_fk_money`
	FOREIGN KEY ( `money` ) REFERENCES `closedcash` ( `money` );

-- Update foreign keys of reservation_customers
ALTER TABLE `reservation_customers` ADD CONSTRAINT `res_cust_fk_1`
	FOREIGN KEY ( `id` ) REFERENCES `reservations` ( `id` );

ALTER TABLE `reservation_customers` ADD CONSTRAINT `res_cust_fk_2`
	FOREIGN KEY ( `customer` ) REFERENCES `customers` ( `id` );

-- Update foreign keys of shift_breaks
ALTER TABLE `shift_breaks` ADD CONSTRAINT `shift_breaks_breakid`
	FOREIGN KEY ( `breakid` ) REFERENCES `breaks` ( `id` );

ALTER TABLE `shift_breaks` ADD CONSTRAINT `shift_breaks_shiftid`
	FOREIGN KEY ( `shiftid` ) REFERENCES `shifts` ( `id` );

-- Update foreign keys of stockcurrent
ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `stockcurrent` ADD CONSTRAINT `stockcurrent_fk_2`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

-- Update foreign keys of stockdiary
ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_fk_1`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `stockdiary` ADD CONSTRAINT `stockdiary_fk_2`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

-- Update foreign keys of stocklevel
ALTER TABLE `stocklevel` ADD CONSTRAINT `stocklevel_location`
	FOREIGN KEY ( `location` ) REFERENCES `locations` ( `id` );

ALTER TABLE `stocklevel` ADD CONSTRAINT `stocklevel_product`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

-- Update foreign keys of taxes
ALTER TABLE `taxes` ADD CONSTRAINT `taxes_cat_fk`
	FOREIGN KEY ( `category` ) REFERENCES `taxcategories` ( `id` );

ALTER TABLE `taxes` ADD CONSTRAINT `taxes_custcat_fk`
	FOREIGN KEY ( `custcategory` ) REFERENCES `taxcustcategories` ( `id` );

ALTER TABLE `taxes` ADD CONSTRAINT `taxes_taxes_fk`
	FOREIGN KEY ( `parentid` ) REFERENCES `taxes` ( `id` );

-- Update foreign keys of taxlines
ALTER TABLE `taxlines` ADD CONSTRAINT `taxlines_receipt`
	FOREIGN KEY ( `receipt` ) REFERENCES `receipts` ( `id` );

ALTER TABLE `taxlines` ADD CONSTRAINT `taxlines_tax`
	FOREIGN KEY ( `taxid` ) REFERENCES `taxes` ( `id` );

-- Update foreign keys of ticketlines
ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_attsetinst`
	FOREIGN KEY ( `attributesetinstance_id` ) REFERENCES `attributesetinstance` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_2`
	FOREIGN KEY ( `product` ) REFERENCES `products` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_3`
	FOREIGN KEY ( `taxid` ) REFERENCES `taxes` ( `id` );

ALTER TABLE `ticketlines` ADD CONSTRAINT `ticketlines_fk_ticket`
	FOREIGN KEY ( `ticket` ) REFERENCES `tickets` ( `id` );

-- Update foreign keys of tickets
ALTER TABLE `tickets` ADD CONSTRAINT `tickets_customers_fk`
	FOREIGN KEY ( `customer` ) REFERENCES `customers` ( `id` );

ALTER TABLE `tickets` ADD CONSTRAINT `tickets_fk_2`
	FOREIGN KEY ( `person` ) REFERENCES `people` ( `id` );

ALTER TABLE `tickets` ADD CONSTRAINT `tickets_fk_id`
	FOREIGN KEY ( `id` ) REFERENCES `receipts` ( `id` );

-- *****************************************************************************

 -- ADD roles
INSERT INTO roles(id, name, permissions) VALUES('0', 'Rol Administrador', $FILE{/com/openbravo/pos/templates/Role.Administrator.xml} );
INSERT INTO roles(id, name, permissions) VALUES('1', 'Rol Gerente', $FILE{/com/openbravo/pos/templates/Role.Manager.xml} );
INSERT INTO roles(id, name, permissions) VALUES('2', 'Rol Empleado', $FILE{/com/openbravo/pos/templates/Role.Employee.xml} );
INSERT INTO roles(id, name, permissions) VALUES('3', 'Rol Invitado', $FILE{/com/openbravo/pos/templates/Role.Guest.xml} );

-- ADD people
INSERT INTO people(id, name, apppassword, role, visible, image) VALUES ('0', 'Administrador', NULL, '0', TRUE, NULL);
INSERT INTO people(id, name, apppassword, role, visible, image) VALUES ('1', 'Gerente', NULL, '1', TRUE, NULL);
INSERT INTO people(id, name, apppassword, role, visible, image) VALUES ('2', 'Empleado', NULL, '2', TRUE, NULL);
INSERT INTO people(id, name, apppassword, role, visible, image) VALUES ('3', 'Invitado', NULL, '3', TRUE, NULL);
INSERT INTO people(id, name, apppassword, role, visible, image) VALUES ('4', 'Soporte Técnico', 'sha1:860F424F34E69DB63EDEAD53DDF72E6D2C43C5C4', '0', TRUE, NULL);

-- ADD permisos --
INSERT INTO `dbpermissions` (`CLASSNAME`, `SECTION`, `DISPLAYNAME`, `DESCRIPTION`, `SITEGUID`, `SFLAG`) VALUES
('com.openbravo.pos.sales.JPanelTicketSales', '##label.sectionmain', '##label.displayname1', '##label.description1', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.sales.JPanelTicketEdits', '##label.sectionmain', '##label.displayname2', '##label.description2', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.customers.CustomersPayment', '##label.sectionmain', '##label.displayname3', '##label.description3', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.panels.JPanelPayments', '##label.sectionmain', '##label.displayname4', '##label.description4', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.panels.JPanelCloseMoney', '##label.sectionmain', '##label.displayname5', '##label.description5', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.panels.JPanelCloseMoneyReprint', '##label.sectionsales', '##label.displayname6', '##label.description6', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.Override', '##label.sectionsales', '##label.displayname7', '##label.description7', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.ViewSharedTicket', '##label.sectionsales', '##label.displayname8', '##label.description8', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.DeleteLines', '##label.sectionsales', '##label.displayname9', '##label.description9', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.EditLines', '##label.sectionsales', '##label.displayname10', '##label.description10', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.EditTicket', '##label.sectionsales', '##label.displayname11', '##label.description11', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.RefundTicket', '##label.sectionsales', '##label.displayname12', '##label.description12', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.PrintTicket', '##label.sectionsales', '##label.displayname13', '##label.description13', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.Total', '##label.sectionsales', '##label.displayname14', '##label.description14', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.ChangeTaxOptions', '##label.sectionsales', '##label.displayname15', '##label.description15', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_cashflow.bs', '##label.sectionsales', '##label.displayname16', '##label.description16', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_cashregisterlog.bs', '##label.sectionsales', '##label.displayname17', '##label.description17', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_categorysales.bs', '##label.sectionsales', '##label.displayname18', '##label.description18', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_categorysales_1.bs', '##label.sectionsales', '##label.displayname19', '##label.description19', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_closedpos.bs', '##label.sectionsales', '##label.displayname20', '##label.description20', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_closedpos_1.bs', '##label.sectionsales', '##label.displayname21', '##label.description21', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_closedproducts.bs', '##label.sectionsales', '##label.displayname22', '##label.description22', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_closedproducts_1.bs', '##label.sectionsales', '##label.displayname23', '##label.description23', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_extendedcashregisterlog.bs', '##label.sectionsales', '##label.displayname24', '##label.description24', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_extproducts.bs', '##label.sectionsales', '##label.displayname25', '##label.description25', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_paymentreport.bs', '##label.sectionsales', '##label.displayname26', '##label.description26', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_productsalesprofit.bs', '##label.sectionsales', '##label.displayname27', '##label.description27', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_taxcatsales.bs', '##label.sectionsales', '##label.displayname29', '##label.description29', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_taxes.bs', '##label.sectionsales', '##label.displayname30', '##label.description30', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_chart_sales.bs', '##label.sectionsales', '##label.displayname31', '##label.description31', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_chart_piesalescat.bs', '##label.sectionsales', '##label.displayname32', '##label.description32', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_chart_productsales.bs', '##label.sectionsales', '##label.displayname33', '##label.description33', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_chart_timeseriesproduct.bs', '##label.sectionsales', '##label.displayname34', '##label.description34', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_chart_top10sales.bs', '##label.sectionsales', '##label.displayname35', '##label.description35', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.bank', '##label.sectionpayments', '##label.displayname36', '##label.description36', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.cash', '##label.sectionpayments', '##label.displayname37', '##label.description37', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.cheque', '##label.sectionpayments', '##label.displayname38', '##label.description38', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.voucher', '##label.sectionpayments', '##label.displayname39', '##label.description39', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.magcard', '##label.sectionpayments', '##label.displayname40', '##label.description40', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.slip', '##label.sectionpayments', '##label.displayname41', '##label.description41', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.free', '##label.sectionpayments', '##label.displayname42', '##label.description42', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('payment.debt', '##label.sectionpayments', '##label.displayname43', '##label.description43', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('refund.cash', '##label.sectionpayments', '##label.displayname44', '##label.description44', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('refund.cheque', '##label.sectionpayments', '##label.displayname45', '##label.description45', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('refund.voucher', '##label.sectionpayments', '##label.displayname46', '##label.description46', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('refund.magcard', '##label.sectionpayments', '##label.displayname47', '##label.description47', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.totaldiscount', '##label.sectionbuttons', '##label.displayname48', '##label.description48', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.print', '##label.sectionbuttons', '##label.displayname49', '##label.description49', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.opendrawer', '##label.sectionbuttons', '##label.displayname50', '##label.description50', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.open', '##label.sectionbuttons', '##label.displayname51', '##label.description51', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.linediscount', '##label.sectionbuttons', '##label.displayname52', '##label.description52', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.sendorder', '##label.sectionbuttons', '##label.displayname53', '##label.description53', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.refundit', '##label.sectionbuttons', '##label.displayname54', '##label.description54', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('button.scharge', '##label.sectionbuttons', '##label.displayname55', '##label.description55', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('sales.PrintKitchen', '##label.sectionbuttons', '##label.displayname56', '##label.description56', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuCustomers', '##label.sectionadmin', '##label.displayname57', '##label.description57', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuSuppliers', '##label.sectionadmin', '##label.displayname58', '##label.description58', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuStockManagement', '##label.sectionadmin', '##label.displayname59', '##label.description59', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuEmployees', '##label.sectionadmin', '##label.displayname60', '##label.description60', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuMaintenance', '##label.sectionadmin', '##label.displayname61', '##label.description61', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.customers.CustomersPanel', '##label.sectioncustomers', '##label.displayname62', '##label.description62', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers.bs', '##label.sectioncustomers', '##label.displayname63', '##label.description63', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_sales.bs', '##label.sectioncustomers', '##label.displayname64', '##label.description64', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_debtors.bs', '##label.sectioncustomers', '##label.displayname65', '##label.description65', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_diary.bs', '##label.sectioncustomers', '##label.displayname66', '##label.description66', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_cards.bs', '##label.sectioncustomers', '##label.displayname67', '##label.description67', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_list.bs', '##label.sectioncustomers', '##label.displayname68', '##label.description68', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/customers_export.bs', '##label.sectioncustomers', '##label.displayname69', '##label.description69', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.suppliers.SuppliersPanel', '##label.sectionsuppliers', '##label.displayname70', '##label.description70', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers.bs', '##label.sectionsuppliers', '##label.displayname71', '##label.description71', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_b.bs', '##label.sectionsuppliers', '##label.displayname72', '##label.description72', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_creditors.bs', '##label.sectionsuppliers', '##label.displayname73', '##label.description73', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_diary.bs', '##label.sectionsuppliers', '##label.displayname74', '##label.description74', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_list.bs', '##label.sectionsuppliers', '##label.displayname75', '##label.description75', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_sales.bs', '##label.sectionsuppliers', '##label.displayname76', '##label.description76', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_export.bs', '##label.sectionsuppliers', '##label.displayname77', '##label.description77', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/suppliers_products.bs', '##label.sectionsuppliers', '##label.displayname78', '##label.description78', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.AttributesPanel', '##label.sectionstock', '##label.displayname79', '##label.description79', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.AttributeValuesPanel', '##label.sectionstock', '##label.displayname80', '##label.description80', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.AttributeSetsPanel', '##label.sectionstock', '##label.displayname81', '##label.description81', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.AttributeUsePanel', '##label.sectionstock', '##label.displayname82', '##label.description82', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.AuxiliarPanel', '##label.sectionstock', '##label.displayname83', '##label.description83', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.BundlePanel', '##label.sectionstock', '##label.displayname84', '##label.description84', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.CategoriesPanel', '##label.sectionstock', '##label.displayname85', '##label.description85', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.ProductsPanel', '##label.sectionstock', '##label.displayname86', '##label.description86', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.ProductsWarehousePanel', '##label.sectionstock', '##label.displayname87', '##label.description87', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.StockDiaryPanel', '##label.sectionstock', '##label.displayname88', '##label.description88', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.StockManagement', '##label.sectionstock', '##label.displayname89', '##label.description89', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/barcode_sheet.bs', '##label.sectionstock', '##label.displayname90', '##label.description90', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/barcode_sheet_jm.bs', '##label.sectionstock', '##label.displayname91', '##label.description91', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/barcode_shelfedgelabels.bs', '##label.sectionstock', '##label.displayname92', '##label.description92', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventory.bs', '##label.sectionstock', '##label.displayname93', '##label.description93', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventoryb.bs', '##label.sectionstock', '##label.displayname94', '##label.description94', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventory_diary.bs', '##label.sectionstock', '##label.displayname95', '##label.description95', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventorybroken.bs', '##label.sectionstock', '##label.displayname96', '##label.description96', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventorydiff.bs', '##label.sectionstock', '##label.displayname97', '##label.description97', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventorydiffdetail.bs', '##label.sectionstock', '##label.displayname98', '##label.description98', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventorylistdetail.bs', '##label.sectionstock', '##label.displayname99', '##label.description99', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/productscatalog.bs', '##label.sectionstock', '##label.displayname100', '##label.description100', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/products.bs', '##label.sectionstock', '##label.displayname101', '##label.description101', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/productlabels.bs', '##label.sectionstock', '##label.displayname102', '##label.description102', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/salecatalog.bs', '##label.sectionstock', '##label.displayname103', '##label.description103', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.TaxCategoriesPanel', '##label.sectionmaintenance', '##label.displayname105', '##label.description105', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.TaxCustCategoriesPanel', '##label.sectionmaintenance', '##label.displayname106', '##label.description106', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.TaxPanel', '##label.sectionmaintenance', '##label.displayname107', '##label.description107', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.inventory.UomPanel', '##label.sectionmaintenance', '##label.displayname108', '##label.description108', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.admin.PeoplePanel', '##label.sectionmaintenance', '##label.displayname109', '##label.description109', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.admin.RolesPanel', '##label.sectionmaintenance', '##label.displayname110', '##label.description110', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.admin.ResourcesPanel', '##label.sectionmaintenance', '##label.displayname111', '##label.description111', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.mant.JPanelFloors', '##label.sectionmaintenance', '##label.displayname112', '##label.description112', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.mant.JPanelPlaces', '##label.sectionmaintenance', '##label.displayname113', '##label.description113', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.voucher.VoucherPanel', '##label.sectionmaintenance', '##label.displayname114', '##label.description114', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/people.bs', '##label.sectionmaintenance', '##label.displayname115', '##label.description115', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/usersales.bs', '##label.sectionmaintenance', '##label.displayname116', '##label.description116', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/usernosales.bs', '##label.sectionmaintenance', '##label.displayname117', '##label.description117', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.imports.JPanelCSV', '##label.sectiontools', '##label.displayname118', '##label.description118', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.imports.JPanelCSVImport', '##label.sectiontools', '##label.displayname119', '##label.description119', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.imports.CustomerCSVImport', '##label.sectiontools', '##label.displayname120', '##label.description120', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.imports.SuppliersCSVImport', '##label.sectiontools', '##label.displayname121', '##label.description121', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.imports.JPanelCSVCleardb', '##label.sectiontools', '##label.displayname122', '##label.description122', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.unicenta.pos.transfer.Transfer', '##label.sectiontools', '##label.displayname123', '##label.description123', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/badprice.bs', '##label.sectiontools', '##label.displayname124', '##label.description124', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/invalidcategory.bs', '##label.sectiontools', '##label.displayname125', '##label.description125', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/invaliddata.bs', '##label.sectiontools', '##label.displayname126', '##label.description126', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/missingdata.bs', '##label.sectiontools', '##label.displayname127', '##label.description127', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/newproducts.bs', '##label.sectiontools', '##label.displayname128', '##label.description128', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/updatedprices.bs', '##label.sectiontools', '##label.displayname129', '##label.description129', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.epm.BreaksPanel', '##label.sectionpresence', '##label.displayname130', '##label.description130', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.epm.LeavesPanel', '##label.sectionpresence', '##label.displayname131', '##label.description131', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/dailypresencereport.bs', '##label.sectionpresence', '##label.displayname132', '##label.description132', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/dailybreaksreport.bs', '##label.sectionpresence', '##label.displayname133', '##label.description133', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/dailyschedulereport.bs', '##label.sectionpresence', '##label.displayname134', '##label.description134', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/performancereport.bs', '##label.sectionpresence', '##label.displayname135', '##label.description135', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.epm.JPanelEmployeePresence', '##label.sectionsystem', '##label.displayname136', '##label.description136', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.panels.JPanelPrinter', '##label.sectionsystem', '##label.displayname137', '##label.description137', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.config.JPanelConfiguration', '##label.sectionsystem', '##label.displayname138', '##label.description138', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('Menu.ChangePassword', '##label.sectionsystem', '##label.displayname139', '##label.description139', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.possync.ProductsSyncCreate', '##label.sectionsync', '##label.displayname140', '##label.description140', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.possync.OrdersSyncCreate', '##label.sectionsync', '##label.displayname141', '##label.description141', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('com.openbravo.pos.forms.MenuSalesManagement', '##label.sectionadmin', '##label.displayname200', '##label.description200', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/comisionsales.bs', '##label.sectionpresence', '##label.displayname201', '##label.description201', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_saletaxes.bs', '##label.sectionsales', '##label.displayname202', '##label.description202', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/vencimiento.bs', '##label.sectionstock', '##label.displayname203', '##label.description203', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventoryc.bs', '##label.sectionstock', '##label.displayname204', '##label.description204', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/inventoryd.bs', '##label.sectionstock', '##label.displayname205', '##label.description205', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/sales_productsalesprofit_summary.bs', '##label.sectionsales', '##label.displayname206', '##label.description206', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1),
('/com/openbravo/reports/itemsremovalreport.bs', '##label.sectionsales', '##label.displayname207', '##label.description207', '97708bbd-cd5b-40f3-bbc5-852e364d8ac2', 1);

-- ADD resources --
-- MENU
INSERT INTO resources(id, name, restype, content) VALUES('0', 'Menu.Root', 0, $FILE{/com/openbravo/pos/templates/Menu.Root.txt});

-- IMAGES
INSERT INTO resources(id, name, restype, content) VALUES('1', 'coin.01', 1, $FILE{/com/openbravo/pos/templates/coin.01.png});
INSERT INTO resources(id, name, restype, content) VALUES('2', 'coin.02', 1, $FILE{/com/openbravo/pos/templates/coin.02.png});
INSERT INTO resources(id, name, restype, content) VALUES('3', 'coin.05', 1, $FILE{/com/openbravo/pos/templates/coin.05.png});
INSERT INTO resources(id, name, restype, content) VALUES('4', 'coin.1', 1, $FILE{/com/openbravo/pos/templates/coin.1.png});
INSERT INTO resources(id, name, restype, content) VALUES('5', 'coin.10', 1, $FILE{/com/openbravo/pos/templates/coin.10.png});
INSERT INTO resources(id, name, restype, content) VALUES('6', 'coin.2', 1, $FILE{/com/openbravo/pos/templates/coin.2.png});
INSERT INTO resources(id, name, restype, content) VALUES('7', 'coin.20', 1, $FILE{/com/openbravo/pos/templates/coin.20.png});
INSERT INTO resources(id, name, restype, content) VALUES('8', 'coin.50', 1, $FILE{/com/openbravo/pos/templates/coin.50.png});
INSERT INTO resources(id, name, restype, content) VALUES('9', 'img.cash', 1, $FILE{/com/openbravo/pos/templates/img.cash.png});
INSERT INTO resources(id, name, restype, content) VALUES('10', 'img.cashdrawer', 1, $FILE{/com/openbravo/pos/templates/img.cashdrawer.png});
INSERT INTO resources(id, name, restype, content) VALUES('11', 'img.discount', 1, $FILE{/com/openbravo/pos/templates/img.discount.png});
INSERT INTO resources(id, name, restype, content) VALUES('12', 'img.discount_b', 1, $FILE{/com/openbravo/pos/templates/img.discount_b.png});
INSERT INTO resources(id, name, restype, content) VALUES('13', 'img.empty', 1, $FILE{/com/openbravo/pos/templates/img.empty.png});
INSERT INTO resources(id, name, restype, content) VALUES('14', 'img.heart', 1, $FILE{/com/openbravo/pos/templates/img.heart.png});
INSERT INTO resources(id, name, restype, content) VALUES('15', 'img.keyboard_32', 1, $FILE{/com/openbravo/pos/templates/img.keyboard_32.png});
INSERT INTO resources(id, name, restype, content) VALUES('16', 'img.kit_print', 1, $FILE{/com/openbravo/pos/templates/img.kit_print.png});
INSERT INTO resources(id, name, restype, content) VALUES('17', 'img.no_photo', 1, $FILE{/com/openbravo/pos/templates/img.no_photo.png});
INSERT INTO resources(id, name, restype, content) VALUES('18', 'img.refundit', 1, $FILE{/com/openbravo/pos/templates/img.refundit.png});
INSERT INTO resources(id, name, restype, content) VALUES('19', 'img.run_script', 1, $FILE{/com/openbravo/pos/templates/img.run_script.png});
INSERT INTO resources(id, name, restype, content) VALUES('20', 'img.ticket_print', 1, $FILE{/com/openbravo/pos/templates/img.ticket_print.png});
INSERT INTO resources(id, name, restype, content) VALUES('21', 'img.user', 1, $FILE{/com/openbravo/pos/templates/img.user.png});
INSERT INTO resources(id, name, restype, content) VALUES('22', 'note.50', 1, $FILE{/com/openbravo/pos/templates/note.50.png});
INSERT INTO resources(id, name, restype, content) VALUES('23', 'note.20', 1, $FILE{/com/openbravo/pos/templates/note.20.png});
INSERT INTO resources(id, name, restype, content) VALUES('24', 'note.10', 1, $FILE{/com/openbravo/pos/templates/note.10.png});
INSERT INTO resources(id, name, restype, content) VALUES('25', 'note.5', 1, $FILE{/com/openbravo/pos/templates/note.5.png});

-- PRINTER
INSERT INTO resources(id, name, restype, content) VALUES('26', 'Printer.CloseCash.Preview', 0, $FILE{/com/openbravo/pos/templates/Printer.CloseCash.Preview.xml});
INSERT INTO resources(id, name, restype, content) VALUES('27', 'Printer.CloseCash', 0, $FILE{/com/openbravo/pos/templates/Printer.CloseCash.xml});
INSERT INTO resources(id, name, restype, content) VALUES('28', 'Printer.CustomerPaid', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid.xml});
INSERT INTO resources(id, name, restype, content) VALUES('29', 'Printer.CustomerPaid2', 0, $FILE{/com/openbravo/pos/templates/Printer.CustomerPaid2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('30', 'Printer.FiscalTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.FiscalTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('31', 'Printer.Inventory', 0, $FILE{/com/openbravo/pos/templates/Printer.Inventory.xml});
INSERT INTO resources(id, name, restype, content) VALUES('32', 'Printer.OpenDrawer', 0, $FILE{/com/openbravo/pos/templates/Printer.OpenDrawer.xml});
INSERT INTO resources(id, name, restype, content) VALUES('33', 'Printer.PartialCash', 0, $FILE{/com/openbravo/pos/templates/Printer.PartialCash.xml});
INSERT INTO resources(id, name, restype, content) VALUES('34', 'Printer.PrintLastTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.PrintLastTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('35', 'Printer.Product', 0, $FILE{/com/openbravo/pos/templates/Printer.Product.xml});
INSERT INTO resources(id, name, restype, content) VALUES('36', 'Printer.ReprintTicket', 0, $FILE{/com/openbravo/pos/templates/Printer.ReprintTicket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('37', 'Printer.Start', 0, $FILE{/com/openbravo/pos/templates/Printer.Start.xml});
INSERT INTO resources(id, name, restype, content) VALUES('38', 'Printer.Ticket.P1', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P1.xml});
INSERT INTO resources(id, name, restype, content) VALUES('39', 'Printer.Ticket.P2', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('40', 'Printer.Ticket.P3', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P3.xml});
INSERT INTO resources(id, name, restype, content) VALUES('41', 'Printer.Ticket.P4', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P4.xml});
INSERT INTO resources(id, name, restype, content) VALUES('42', 'Printer.Ticket.P5', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P5.xml});
INSERT INTO resources(id, name, restype, content) VALUES('43', 'Printer.Ticket.P6', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.P6.xml});
INSERT INTO resources(id, name, restype, content) VALUES('44', 'Printer.Ticket', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket.xml});
INSERT INTO resources(id, name, restype, content) VALUES('45', 'Printer.Ticket2', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket2.xml});
INSERT INTO resources(id, name, restype, content) VALUES('46', 'Printer.TicketClose', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketClose.xml});
INSERT INTO resources(id, name, restype, content) VALUES('47', 'Printer.TicketRemote', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketRemote.xml});
INSERT INTO resources(id, name, restype, content) VALUES('48', 'Printer.TicketLine', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketLine.xml});
INSERT INTO resources(id, name, restype, content) VALUES('49', 'Printer.TicketNew', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketLine.xml});
INSERT INTO resources(id, name, restype, content) VALUES('50', 'Printer.TicketPreview', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketPreview.xml});
INSERT INTO resources(id, name, restype, content) VALUES('51', 'Printer.TicketTotal', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketTotal.xml});
INSERT INTO resources(id, name, restype, content) VALUES('52', 'Printer.Ticket.Logo', 1, $FILE{/com/openbravo/pos/templates/printer.ticket.logo.png});

-- SCRIPTS
INSERT INTO resources(id, name, restype, content) VALUES('53', 'script.AddLineNote', 0, $FILE{/com/openbravo/pos/templates/script.AddLineNote.txt});
INSERT INTO resources(id, name, restype, content) VALUES('54', 'script.Event.Total', 0, $FILE{/com/openbravo/pos/templates/script.Event.Total.txt});
INSERT INTO resources(id, name, restype, content) VALUES('55', 'script.Keyboard', 0, $FILE{/com/openbravo/pos/templates/script.Keyboard.txt});
INSERT INTO resources(id, name, restype, content) VALUES('56', 'script.linediscount', 0, $FILE{/com/openbravo/pos/templates/script.linediscount.txt});
INSERT INTO resources(id, name, restype, content) VALUES('57', 'script.ReceiptConsolidate', 0, $FILE{/com/openbravo/pos/templates/script.ReceiptConsolidate.txt});
INSERT INTO resources(id, name, restype, content) VALUES('58', 'script.Refundit', 0, $FILE{/com/openbravo/pos/templates/script.Refundit.txt});
INSERT INTO resources(id, name, restype, content) VALUES('59', 'script.SendOrder', 0, $FILE{/com/openbravo/pos/templates/script.SendOrder.txt});
INSERT INTO resources(id, name, restype, content) VALUES('60', 'script.ServiceCharge', 0, $FILE{/com/openbravo/pos/templates/script.script.ServiceCharge.txt});
INSERT INTO resources(id, name, restype, content) VALUES('61', 'script.SetPerson', 0, $FILE{/com/openbravo/pos/templates/script.SetPerson.txt});
INSERT INTO resources(id, name, restype, content) VALUES('62', 'script.StockCurrentAdd', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentAdd.txt});
INSERT INTO resources(id, name, restype, content) VALUES('63', 'script.StockCurrentSet', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentSet.txt});
INSERT INTO resources(id, name, restype, content) VALUES('64', 'script.totaldiscount', 0, $FILE{/com/openbravo/pos/templates/script.totaldiscount.txt});

-- SYSTEM
INSERT INTO resources(id, name, restype, content) VALUES('65', 'payment.cash', 0, $FILE{/com/openbravo/pos/templates/payment.cash.txt});
INSERT INTO resources(id, name, restype, content) VALUES('66', 'ticket.addline', 0, $FILE{/com/openbravo/pos/templates/ticket.addline.txt});
INSERT INTO resources(id, name, restype, content) VALUES('67', 'ticket.change', 0, $FILE{/com/openbravo/pos/templates/ticket.change.txt});
INSERT INTO resources(id, name, restype, content) VALUES('68', 'Ticket.Buttons', 0, $FILE{/com/openbravo/pos/templates/Ticket.Buttons.xml});
INSERT INTO resources(id, name, restype, content) VALUES('69', 'Ticket.Close', 0, $FILE{/com/openbravo/pos/templates/Ticket.Close.xml});
INSERT INTO resources(id, name, restype, content) VALUES('70', 'Ticket.Discount', 0, $FILE{/com/openbravo/pos/templates/Ticket.Discount.xml});
INSERT INTO resources(id, name, restype, content) VALUES('71', 'Ticket.Line', 0, $FILE{/com/openbravo/pos/templates/Ticket.Line.xml});
INSERT INTO resources(id, name, restype, content) VALUES('72', 'ticket.removeline', 0, $FILE{/com/openbravo/pos/templates/ticket.removeline.txt});
INSERT INTO resources(id, name, restype, content) VALUES('73', 'ticket.setline', 0, $FILE{/com/openbravo/pos/templates/ticket.setline.txt});
INSERT INTO resources(id, name, restype, content) VALUES('74', 'Ticket.TicketLineTaxesIncluded', 0, $FILE{/com/openbravo/pos/templates/Ticket.TicketLineTaxesIncluded.xml});
INSERT INTO resources(id, name, restype, content) VALUES('75', 'Window.Logo', 1, $FILE{/com/openbravo/pos/templates/window.logo.png});
INSERT INTO resources(id, name, restype, content) VALUES('76', 'Window.Title', 0, $FILE{/com/openbravo/pos/templates/Window.Title.txt});

-- IMAGES
INSERT INTO resources(id, name, restype, content) VALUES('100', 'coin.50pesos', 1, $FILE{/com/openbravo/pos/templates/coin.50pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('101', 'coin.100pesos', 1, $FILE{/com/openbravo/pos/templates/coin.100pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('102', 'coin.200pesos', 1, $FILE{/com/openbravo/pos/templates/coin.200pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('103', 'coin.500pesos', 1, $FILE{/com/openbravo/pos/templates/coin.500pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('104', 'coin.1000pesos', 1, $FILE{/com/openbravo/pos/templates/coin.1000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('105', 'banknote.1000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.1000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('106', 'banknote.2000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.2000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('107', 'banknote.5000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.5000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('108', 'banknote.10000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.10000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('109', 'banknote.20000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.20000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('110', 'banknote.50000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.50000pesos.png});
INSERT INTO resources(id, name, restype, content) VALUES('111', 'banknote.100000pesos', 1, $FILE{/com/openbravo/pos/templates/banknote.100000pesos.png});

-- SCRIPTS
INSERT INTO resources(id, name, restype, content) VALUES('120', 'script.expiry', 0, $FILE{/com/openbravo/pos/templates/script.expiry.txt});
INSERT INTO resources(id, name, restype, content) VALUES('121', 'script.happyhour', 0, $FILE{/com/openbravo/pos/templates/script.happyhour.txt});
INSERT INTO resources(id, name, restype, content) VALUES('122', 'event.total', 0, $FILE{/com/openbravo/pos/templates/event.total.txt});
INSERT INTO resources(id, name, restype, content) VALUES('123', 'abrir.cajon', 0, $FILE{/com/openbravo/pos/templates/abrir.cajon.txt});
INSERT INTO resources(id, name, restype, content) VALUES('124', 'Printer.Ticket_A4', 0, $FILE{/com/openbravo/pos/templates/Printer.Ticket_A4.xml});
INSERT INTO resources(id, name, restype, content) VALUES('125', 'Ticket.LineDisplay', 0, $FILE{/com/openbravo/pos/templates/Ticket.LineDisplay.xml});
INSERT INTO resources(id, name, restype, content) VALUES('126', 'Ticket.CloseTimer', 0, $FILE{/com/openbravo/pos/templates/Ticket.CloseTimer.xml});
INSERT INTO resources(id, name, restype, content) VALUES('127', 'Display.Message', 0, $FILE{/com/openbravo/pos/templates/Display.Message.xml});


-- ADD CATEGORIES
INSERT INTO categories(id, name) VALUES ('000', 'Category Standard');

-- ADD TAXCATEGORIES
INSERT INTO taxcategories(id, name) VALUES ('000', 'Tax Exempt');
INSERT INTO taxcategories(id, name) VALUES ('001', 'Tax Standard');

-- ADD TAXES
INSERT INTO taxes(id, name, category, custcategory, parentid, rate, ratecascade, rateorder) VALUES ('000', 'Tax Exempt', '000', NULL, NULL, 0, FALSE, NULL);
INSERT INTO taxes(id, name, category, custcategory, parentid, rate, ratecascade, rateorder) VALUES ('001', 'Tax Standard', '001', NULL, NULL, 0.20, FALSE, NULL);

-- ADD PRODUCTS
INSERT INTO products(id, reference, code, name, category, taxcat, isservice, display, printto) 
VALUES ('xxx999_999xxx_x9x9x9', 'xxx999', 'xxx999', 'PRODUCTO GENERICO', '000', '001', 1, '<html><center>PRODUCTO GENERICO', '1');
INSERT INTO products(id, reference, code, name, category, taxcat, isservice, display, printto) 
VALUES ('xxx998_998xxx_x8x8x8', 'xxx998', 'xxx998', '****', '000', '001', 1, '<html><center>****', '1');

-- ADD PRODUCTS_CAT
INSERT INTO products_cat(product) VALUES ('xxx999_999xxx_x9x9x9');
INSERT INTO products_cat(product) VALUES ('xxx998_998xxx_x8x8x8');

-- ADD LOCATION
INSERT INTO locations(id, name, address) VALUES ('0','Location 1','Local');

-- ADD SUPPLIERS
INSERT INTO suppliers(id, searchkey, name) VALUES ('0','executerpos','Executer Pos');

-- ADD UOM
INSERT INTO uom(id, name) VALUES ('0','Unidad');

-- ADD FLOORS
INSERT INTO floors(id, name, image) VALUES ('0', 'Restaurant floor', $FILE{/com/openbravo/pos/templates/restaurant_floor.png});

-- ADD PLACES
INSERT INTO places(id, name, x, y, floor) VALUES ('1', 'Mesa 1', 100, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('2', 'Mesa 2', 250, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('3', 'Mesa 3', 400, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('4', 'Mesa 4', 550, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('5', 'Mesa 5', 700, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('6', 'Mesa 6', 850, 50, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('7', 'Mesa 7', 100, 150, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('8', 'Mesa 8', 250, 150, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('9', 'Mesa 9', 400, 150, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('10', 'Mesa 10', 550, 150, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('11', 'Mesa 11', 700, 150, '0');
INSERT INTO places(id, name, x, y, floor) VALUES ('12', 'Mesa 12', 850, 150, '0');

-- ADD SHIFTS
INSERT INTO shifts(id, startshift, endshift, pplid) VALUES ('0', '2016-01-01 00:00:00.001', '2016-01-01 00:00:00.002','0');

-- ADD BREAKS
INSERT INTO breaks(id, name, visible, notes) VALUES ('0', 'Pausa para Almorzar', TRUE, NULL);
INSERT INTO breaks(id, name, visible, notes) VALUES ('1', 'Pausa para el Té', TRUE, NULL);
INSERT INTO breaks(id, name, visible, notes) VALUES ('2', 'Pausa para descansar', TRUE, NULL);

-- ADD SHIFT_BREAKS
INSERT INTO shift_breaks(id, shiftid, breakid, starttime, endtime) VALUES ('0', '0', '0', '2016-01-01 00:00:00.003', '2016-01-01 00:00:00.004');

-- ADD SEQUENCES
INSERT INTO pickup_number VALUES(1);
INSERT INTO ticketsnum VALUES(1);
INSERT INTO ticketsnum_refund VALUES(1);
INSERT INTO ticketsnum_payment VALUES(1);

-- ADD APPLICATION VERSION
INSERT INTO applications(id, name, version) VALUES($APP_ID{}, $APP_NAME{}, $APP_VERSION{});