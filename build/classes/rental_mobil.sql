CREATE DATABASE rental_mobil;
USE rental_mobil;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    PASSWORD VARCHAR(255) NOT NULL
);

INSERT INTO users (username, PASSWORD) VALUES ('admin', '12345678');

CREATE TABLE sewa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    model_mobil VARCHAR(100) NOT NULL,
    tanggal_mulai DATE NOT NULL,
    durasi INT NOT NULL
);
