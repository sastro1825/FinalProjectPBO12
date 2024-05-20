/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 10.4.28-MariaDB : Database - rental_mobil
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rental_mobil` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `rental_mobil`;

/*Table structure for table `list_mobil` */

DROP TABLE IF EXISTS `list_mobil`;

CREATE TABLE `list_mobil` (
  `idMobil` int(11) NOT NULL AUTO_INCREMENT,
  `namaMobil` varchar(50) NOT NULL,
  `tahunMobil` int(4) NOT NULL,
  `nopol` varchar(15) NOT NULL,
  `harga` int(11) NOT NULL,
  `ketersediaan` char(15) NOT NULL,
  PRIMARY KEY (`idMobil`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `list_mobil` */

insert  into `list_mobil`(`idMobil`,`namaMobil`,`tahunMobil`,`nopol`,`harga`,`ketersediaan`) values 
(1,'Toyota Avanza',2022,'B 154',650000,'Tersedia'),
(2,'Daihatsu Xenia',2021,'B 6666 SXW',550000,'Tersedia'),
(3,'Toyota Kijang Innova',2023,'P 4571 BS',950000,'Tidak Tersedia'),
(4,'Toyota Innova Venturer',2021,'N 45I6 RG',800000,'Tersedia'),
(5,'Hyundai Palisade',2022,'M 364 WT',1300000,'Tersedia'),
(6,'Toyota Avanza',2020,'W 1234 ST',500000,'Tidak Tersedia');

/*Table structure for table `sewa` */

DROP TABLE IF EXISTS `sewa`;

CREATE TABLE `sewa` (
  `idSewa` int(11) NOT NULL AUTO_INCREMENT,
  `namaPenyewa` varchar(100) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `noHP` char(15) NOT NULL,
  `tipeMobil` varchar(50) NOT NULL,
  `tahunMobilSewa` int(4) NOT NULL,
  `nopol` varchar(15) NOT NULL,
  `tglSewa` date NOT NULL,
  `tglKembali` date NOT NULL,
  `supir` char(15) NOT NULL,
  `totalHarga` int(11) NOT NULL,
  PRIMARY KEY (`idSewa`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `sewa` */

insert  into `sewa`(`idSewa`,`namaPenyewa`,`nik`,`alamat`,`noHP`,`tipeMobil`,`tahunMobilSewa`,`nopol`,`tglSewa`,`tglKembali`,`supir`,`totalHarga`) values 
(13,'Wahid','520123123','Surabaya','081231231','Toyota Avanza',2020,'W 1234 ST','2024-05-21','2024-05-23','Ya',1200000),
(14,'Hidayat','721231232','Surabaya','0812312312','Toyota Kijang Innova',2023,'P 4571 BS','2024-05-21','2024-05-22','Ya',1050000);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`PASSWORD`) values 
(1,'admin','12345678'),
(2,'staff','12345678');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
