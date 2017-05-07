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
 * Script created by Jack, uniCenta 20/11/2016 08:00:00
 *
 * Create ORDERS table for Remote Display
*/

/* Header line. Object: orders. Script date: 20/11/2016 08:00:00 */
CREATE TABLE IF NOT EXISTS `orders` (
	`id` varchar(50) NOT NULL,
	`orderid` varchar(50) NOT NULL,
	`qty` smallint(6) NOT NULL default '1',
        `details` varchar(255) NOT NULL,
        `attributes` varchar(255) NOT NULL,
        `notes` varchar(255) NOT NULL,
        `ticketid` varchar(50) NOT NULL,
        `ordertime` timestamp default CURRENT_TIMESTAMP,
        `displayid` smallint(6) NOT NULL default '1',
        `auxiliary` smallint(6) NOT NULL default '0',
	PRIMARY KEY  ( `id` )
) ENGINE = InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT = Compact;
 
