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
  `idMobil` INT(11) NOT NULL AUTO_INCREMENT,
  `namaMobil` VARCHAR(50) NOT NULL,
  `tahunMobil` INT(4) NOT NULL,
  `nopol` VARCHAR(15) NOT NULL,
  `harga` INT(11) NOT NULL,
  `ketersediaan` CHAR(15) NOT NULL,
  PRIMARY KEY (`idMobil`)
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `list_mobil` */

INSERT  INTO `list_mobil`(`idMobil`,`namaMobil`,`tahunMobil`,`nopol`,`harga`,`ketersediaan`) VALUES 
(1,'Toyota Avanza',2022,'B 154',650000,'Tersedia'),
(2,'Daihatsu Xenia',2021,'B 6666 SXW',550000,'Tersedia'),
(3,'Toyota Kijang Innova',2023,'P 4571 BS',950000,'Tidak Tersedia'),
(4,'Toyota Innova Venturer',2021,'N 45I6 RG',800000,'Tersedia'),
(5,'Hyundai Palisade',2022,'M 364 WT',1300000,'Tersedia'),
(6,'Toyota Avanza',2020,'W 1234 ST',500000,'Tidak Tersedia');

/*Table structure for table `sewa` */

DROP TABLE IF EXISTS `sewa`;

CREATE TABLE `sewa` (
  `idSewa` INT(11) NOT NULL AUTO_INCREMENT,
  `namaPenyewa` VARCHAR(100) NOT NULL,
  `nik` VARCHAR(20) NOT NULL,
  `alamat` VARCHAR(50) NOT NULL,
  `noHP` CHAR(15) NOT NULL,
  `tipeMobil` VARCHAR(50) NOT NULL,
  `tahunMobilSewa` INT(4) NOT NULL,
  `nopol` VARCHAR(15) NOT NULL,
  `tglSewa` DATE NOT NULL,
  `tglKembali` DATE NOT NULL,
  `supir` CHAR(15) NOT NULL,
  `totalHarga` INT(11) NOT NULL,
  PRIMARY KEY (`idSewa`)
) ENGINE=INNODB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `sewa` */

INSERT  INTO `sewa`(`idSewa`,`namaPenyewa`,`nik`,`alamat`,`noHP`,`tipeMobil`,`tahunMobilSewa`,`nopol`,`tglSewa`,`tglKembali`,`supir`,`totalHarga`) VALUES 
(13,'Wahid','520123123','Surabaya','081231231','Toyota Avanza',2020,'W 1234 ST','2024-05-21','2024-05-23','Ya',1200000),
(14,'Hidayat','721231232','Surabaya','0812312312','Toyota Kijang Innova',2023,'P 4571 BS','2024-05-21','2024-05-22','Ya',1050000);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `PASSWORD` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `mobilkembalian`;

CREATE TABLE `mobilkembalian` (
  `idKembali` INT(11) NOT NULL AUTO_INCREMENT,
  `idSewa` INT(11) NOT NULL,
  `namaPenyewa` VARCHAR(100) NOT NULL,
  `nik` VARCHAR(20) NOT NULL,
  `alamat` VARCHAR(50) NOT NULL,
  `noHP` CHAR(15) NOT NULL,
  `tipeMobil` VARCHAR(50) NOT NULL,
  `tahunMobilSewa` INT(4) NOT NULL,
  `nopol` VARCHAR(15) NOT NULL,
  `tglSewa` DATE NOT NULL,
  `tglKembali` DATE NOT NULL,
  `supir` CHAR(15) NOT NULL,
  `totalHarga` INT(11) NOT NULL,
  PRIMARY KEY (`idKembali`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `users` */

INSERT  INTO `users`(`id`,`username`,`PASSWORD`) VALUES 
(1,'admin','12345678'),
(2,'staff','12345678');

/*procedure for kembalikan mobil*/

DELIMITER //

CREATE PROCEDURE KembalikanMobil(
    IN pNamaPenyewa VARCHAR(100),
    IN pTipeMobil VARCHAR(50),
    IN pTglKembali DATE
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN

        ROLLBACK;
    END;
    START TRANSACTION;

    SELECT * FROM sewa
    WHERE namaPenyewa = pNamaPenyewa
      AND tipeMobil = pTipeMobil
      AND tglKembali IS NULL;

    UPDATE sewa
    SET tglKembali = pTglKembali
    WHERE namaPenyewa = pNamaPenyewa
      AND tipeMobil = pTipeMobil
      AND tglKembali IS NULL;

    SELECT ROW_COUNT() AS RowsUpdated;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No matching rental record found or car already returned';
    ELSE
        INSERT INTO mobilkembalian (idSewa, namaPenyewa, nik, alamat, noHP, tipeMobil, tahunMobilSewa, nopol, tglSewa, tglKembali, supir, totalHarga)
        SELECT idSewa, namaPenyewa, nik, alamat, noHP, tipeMobil, tahunMobilSewa, nopol, tglSewa, pTglKembali, supir, totalHarga
        FROM sewa
        WHERE namaPenyewa = pNamaPenyewa
          AND tipeMobil = pTipeMobil
          AND tglKembali = pTglKembali;

        DELETE FROM sewa
        WHERE namaPenyewa = pNamaPenyewa
          AND tipeMobil = pTipeMobil
          AND tglKembali = pTglKembali;
    END IF;

    COMMIT;
END //

DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
