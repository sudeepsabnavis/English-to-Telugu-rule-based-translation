-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 03, 2015 at 08:39 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nlp`
--

-- --------------------------------------------------------

--
-- Table structure for table `dict`
--

CREATE TABLE IF NOT EXISTS `dict` (
  `eword` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `tword` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `pos` char(20) CHARACTER SET utf8 DEFAULT NULL,
  `past` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `present` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `future` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `sing_plu` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `person` varchar(255) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dict`
--

INSERT INTO `dict` (`eword`, `tword`, `pos`, `past`, `present`, `future`, `gender`, `sing_plu`, `person`) VALUES
('rama', 'రాముడు', 'n', '', '', '', 'm', 's', '3'),
('sita', 'సీత', 'n', '', '', '', 'f', 's', '3'),
('ravan', 'రావణుడు', 'n', '', '', '', 'm', 's', '3'),
('sudeep', 'సుదీప్', 'n', '', '', '', 'm', 's', '3'),
('ravi', 'రవి', 'n', '', '', '', 'm', 's', '3'),
('i', 'నేను', 'pro', '', '', '', 'n', 's', '1'),
('we', 'మేము', 'pro', '', '', '', 'n', 'p', '2'),
('you', 'మీరు', 'pro', '', '', '', 'n', 's', '2'),
('they', 'వాళ్ళు', 'pro', '', '', '', 'n', 'p', '3'),
('he', 'అతడు', 'pro', '', '', '', 'm', 's', '3'),
('she', 'ఆమె', 'pro', '', '', '', 'f', 's', '3'),
('kill', 'చంపు', 'v', 'చంపెను', 'చంపుతున్నాను', 'చంపుతాను', 'n', 's', '1'),
('kill', 'చంపు', 'v', 'చంపారు', 'చంపుతున్నారు', 'చంపుతారు', 'n', 's', '2'),
('kill', 'చంపు', 'v', 'చంపాడు', 'చంపుతున్నాడు', 'చంపుతాడు', 'm', 's', '3'),
('kill', 'చంపు', 'v', 'చంపింది', 'చంపుతున్నది', 'చంపుతుంది', 'f', 's', '3'),
('kill', 'చంపు', 'v', 'చంపాము', 'చంపుతున్నాము', 'చంపుతాము', 'n', 'p', '2'),
('kill', 'చంపు', 'v', 'చంపారు', 'చంపుతున్నారు', 'చంపుతారు', 'n', 'p', '3'),
('is', '', 'v', '', '', '', 'n', 's', '3'),
('are', '', 'v', '', '', '', 'n', 'p', '3'),
('was', '', 'v', '', '', '', 'n', 's', '3'),
('will', '', 'v', '', '', '', 'n', 's', '3'),
('were', '', 'v', '', '', '', 'n', 'p', '3'),
('am', '', 'v', '', '', '', 'n', 's', '1'),
('book', 'పుస్తకము', 'n', '', '', '', 'n', 's', '3'),
('mango', 'మామిడిపండు', 'n', '', '', '', 'n', 's', '3'),
('school', 'పాఠశాల', 'n', '', '', '', 'n', 's', '3'),
('movie', 'చిత్రము', 'n', '', '', '', 'n', 's', '3'),
('food', 'తిండి', 'n', '', '', '', 'n', 's', '3'),
('a', 'ఒక', 'det', '', '', '', 'n', 's', '3'),
('an', 'ఒక', 'det', '', '', '', 'n', 's', '3'),
('the', '', 'det', '', '', '', 'n', 's', '3'),
('boy', 'బాలుడు', 'n', '', '', '', 'm', 's', '3'),
('read', 'చదువు', 'v', 'చదివాను', 'చదువుతున్నాను', 'చదువుతాను', 'n', 's', '1'),
('read', 'చదువు', 'v', 'చదివారు', 'చదువుతున్నారు', 'చదువుతారు', 'n', 's', '2'),
('read', 'చదువు', 'v', 'చదివారు', 'చదువుతున్నారు', 'చదువుతారు', 'n', 'p', '3'),
('read', 'చదువు', 'v', 'చదివాము', 'చదువుతున్నాము', 'చదువుతాము', 'n', 'p', '2'),
('read', 'చదువు', 'v', 'చదివాడు', 'చదువుతున్నాడు', 'చదువుతాడు', 'm', 's', '3'),
('read', 'చదువు', 'v', 'చదివింది', 'చదువుతుంది', 'చదువుతది', 'f', 's', '3');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
