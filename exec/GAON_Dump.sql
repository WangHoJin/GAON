-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: gaon
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rid` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK55c4pcj00ppv1kemtk1vwewiw` (`rid`),
  CONSTRAINT `FK55c4pcj00ppv1kemtk1vwewiw` FOREIGN KEY (`rid`) REFERENCES `room` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guser`
--

DROP TABLE IF EXISTS `guser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guser` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guser`
--

LOCK TABLES `guser` WRITE;
/*!40000 ALTER TABLE `guser` DISABLE KEYS */;
/*!40000 ALTER TABLE `guser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `modified_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `bid` bigint NOT NULL,
  `uid` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq5r8pa46epa992jw0sc1lx3b3` (`bid`),
  KEY `FK8njxm3bonnv7jam57meq6fwcs` (`uid`),
  CONSTRAINT `FK8njxm3bonnv7jam57meq6fwcs` FOREIGN KEY (`uid`) REFERENCES `guser` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKq5r8pa46epa992jw0sc1lx3b3` FOREIGN KEY (`bid`) REFERENCES `board` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_file`
--

DROP TABLE IF EXISTS `post_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `file_size` bigint DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `origin_name` varchar(255) DEFAULT NULL,
  `pid` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqf0v6sbunvwv16mp1omi4et9x` (`pid`),
  CONSTRAINT `FKqf0v6sbunvwv16mp1omi4et9x` FOREIGN KEY (`pid`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_file`
--

LOCK TABLES `post_file` WRITE;
/*!40000 ALTER TABLE `post_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roll_book`
--

DROP TABLE IF EXISTS `roll_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roll_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `rmid` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkyrrt4wdeujxyc9k2gpr4yyxq` (`rmid`,`date`),
  CONSTRAINT `FKqyxae4d72m1i91ag85ksa0hso` FOREIGN KEY (`rmid`) REFERENCES `room_member` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roll_book`
--

LOCK TABLES `roll_book` WRITE;
/*!40000 ALTER TABLE `roll_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `roll_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `created_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `modified_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `host_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_arevbfcloncxciyi0vbx1m4he` (`code`),
  KEY `FK15g422nrxhulrah9swl0ch8g` (`host_id`),
  CONSTRAINT `FK15g422nrxhulrah9swl0ch8g` FOREIGN KEY (`host_id`) REFERENCES `guser` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_member`
--

DROP TABLE IF EXISTS `room_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_member` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `room_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsctyxu6tuv0mn89bont17k5mf` (`room_id`,`user_id`),
  KEY `FKain56lnsvkhsq63khh1bjrj0` (`user_id`),
  CONSTRAINT `FKain56lnsvkhsq63khh1bjrj0` FOREIGN KEY (`user_id`) REFERENCES `guser` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKlmp67erahqx7u5shbkc12p0lw` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_member`
--

LOCK TABLES `room_member` WRITE;
/*!40000 ALTER TABLE `room_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-20  0:27:02
