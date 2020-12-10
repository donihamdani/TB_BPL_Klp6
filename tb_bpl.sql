-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 10 Des 2020 pada 10.56
-- Versi server: 10.4.14-MariaDB
-- Versi PHP: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tb_bpl`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `data_master`
--

CREATE TABLE `data_master` (
  `sku` varchar(200) NOT NULL,
  `nama` varchar(200) NOT NULL,
  `stock` int(200) NOT NULL,
  `harga_beli` int(200) NOT NULL,
  `harga_jual` int(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `data_master`
--

INSERT INTO `data_master` (`sku`, `nama`, `stock`, `harga_beli`, `harga_jual`) VALUES
('A1', 'Buku', 19, 1500, 3000),
('A2', 'Kotak', 77, 3000, 5000),
('A3', 'Gelas', 60, 12000, 20000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `login_terakhir` date NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `login_terakhir`, `email`, `password`) VALUES
('afdhal', '2020-11-30', 'afdhal@gmail.com', '12345'),
('arif', '2020-11-30', 'arif@gmail.com', '12345'),
('aufa', '2020-11-29', 'aufa@gmail.com', '12345'),
('doni', '2020-11-30', 'doni@gmail.com', '12345'),
('thania', '2020-12-10', 'thania@gmail.com', '12345');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `data_master`
--
ALTER TABLE `data_master`
  ADD PRIMARY KEY (`sku`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
