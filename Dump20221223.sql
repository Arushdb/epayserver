CREATE DATABASE  IF NOT EXISTS `deiepay` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `deiepay`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: deiepay
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `adm_program_control`
--

DROP TABLE IF EXISTS `adm_program_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_program_control` (
  `id` int NOT NULL,
  `programid` varchar(10) DEFAULT NULL,
  `session` varchar(12) DEFAULT NULL,
  `date_of_firstlist` datetime DEFAULT NULL,
  `createdby` varchar(45) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `modifiedby` varchar(45) DEFAULT NULL,
  `modifiedtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_program_control`
--

LOCK TABLES `adm_program_control` WRITE;
/*!40000 ALTER TABLE `adm_program_control` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_program_control` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_program_fee_dates`
--

DROP TABLE IF EXISTS `adm_program_fee_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_program_fee_dates` (
  `id` int NOT NULL,
  `programid` varchar(10) DEFAULT NULL,
  `applicationnumber` varchar(12) DEFAULT NULL,
  `duedate` date DEFAULT NULL,
  `lastdate` date DEFAULT NULL,
  `session` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_program_fee_dates`
--

LOCK TABLES `adm_program_fee_dates` WRITE;
/*!40000 ALTER TABLE `adm_program_fee_dates` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_program_fee_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_program_seats`
--

DROP TABLE IF EXISTS `adm_program_seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_program_seats` (
  `id` int NOT NULL,
  `programid` varchar(12) DEFAULT NULL,
  `category` varchar(3) DEFAULT NULL,
  `totalseats` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_program_seats`
--

LOCK TABLES `adm_program_seats` WRITE;
/*!40000 ALTER TABLE `adm_program_seats` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_program_seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_program_seats_filled`
--

DROP TABLE IF EXISTS `adm_program_seats_filled`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_program_seats_filled` (
  `id` int NOT NULL,
  `pgm_seats_id` int DEFAULT NULL,
  `seats_filled` int DEFAULT NULL,
  `session` varchar(12) DEFAULT NULL,
  `status` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_program_seats_filled`
--

LOCK TABLES `adm_program_seats_filled` WRITE;
/*!40000 ALTER TABLE `adm_program_seats_filled` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_program_seats_filled` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `defaulters`
--

DROP TABLE IF EXISTS `defaulters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `defaulters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roll_number` varchar(10) DEFAULT NULL,
  `program_id` varchar(10) DEFAULT NULL,
  `programname` varchar(45) DEFAULT NULL,
  `semester_code` char(4) DEFAULT NULL,
  `defaulter` tinyint DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `createdby` varchar(45) DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `modifiedby` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `defaulters`
--

LOCK TABLES `defaulters` WRITE;
/*!40000 ALTER TABLE `defaulters` DISABLE KEYS */;
INSERT INTO `defaulters` VALUES (1,'1901832','0001067','B.Tech','SM3',1,'2022-12-23 09:49:11',NULL,NULL,NULL);
/*!40000 ALTER TABLE `defaulters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee_dates`
--

DROP TABLE IF EXISTS `fee_dates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee_dates` (
  `id` int NOT NULL AUTO_INCREMENT,
  `session` varchar(12) DEFAULT NULL,
  `duedate` date DEFAULT NULL,
  `lastdate` date DEFAULT NULL,
  `cutoffdate` date DEFAULT NULL,
  `fee_period_start` date DEFAULT NULL COMMENT 'fee collection period start date ',
  `fee_period_to` date DEFAULT NULL COMMENT 'fee-collection_period_end date',
  `type` char(3) DEFAULT NULL,
  `status` char(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`session`,`type`,`duedate`,`cutoffdate`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee_dates`
--

LOCK TABLES `fee_dates` WRITE;
/*!40000 ALTER TABLE `fee_dates` DISABLE KEYS */;
INSERT INTO `fee_dates` VALUES (1,'2022-2023','2022-12-20','2022-12-30','2023-01-10','2022-07-01','2022-12-31','F','ACT'),(2,'2022-2023','2023-01-10','2023-01-29','2023-02-10','2023-01-01','2023-06-30','F','ACT'),(3,'2022-2023','2022-08-01','2022-08-15','2022-08-31','2022-07-01','2022-12-31','M','ACT'),(4,'2022-2023','2022-10-01','2022-10-15','2022-10-31','2022-07-01','2022-12-31','M','ACT');
/*!40000 ALTER TABLE `fee_dates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` int NOT NULL DEFAULT '0',
  `name` varchar(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `lft` int NOT NULL,
  `rgt` int NOT NULL,
  `route` varchar(60) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'root',1,1000,''),(2,'Resident',2,20,''),(3,'Enter Seva',3,4,'seva_detail'),(4,'ResidentMAster',5,6,'resident');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `program_sem_fee`
--

DROP TABLE IF EXISTS `program_sem_fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `program_sem_fee` (
  `id` int NOT NULL,
  `fee` decimal(10,3) DEFAULT NULL,
  `semester` int DEFAULT NULL,
  `programid` char(7) DEFAULT NULL,
  `delaycharges1` decimal(10,3) DEFAULT NULL,
  `delaycharges2` decimal(10,3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `program_sem_fee`
--

LOCK TABLES `program_sem_fee` WRITE;
/*!40000 ALTER TABLE `program_sem_fee` DISABLE KEYS */;
/*!40000 ALTER TABLE `program_sem_fee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` int NOT NULL,
  `refnumber` varchar(45) DEFAULT NULL COMMENT 'Roll number or Application Number',
  `session` varchar(45) DEFAULT NULL,
  `payment_mode` varchar(10) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `remarks` varchar(45) DEFAULT NULL,
  `amount` decimal(10,3) DEFAULT NULL,
  `type` char(3) DEFAULT NULL COMMENT 'APL = Admission Application fee\nJOB  = New Post application fee\nSEM = Semester fee\nMOD = Modular Fee\n',
  `semester_code` varchar(3) DEFAULT NULL COMMENT 'Modular or semester number',
  `remark` varchar(45) DEFAULT NULL,
  `bankrefno` varchar(45) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,'1901831','2022-2023','upi','2022-11-16 00:00:00','ok',250.000,'cr','SM1','ok','ref123','2022-12-19 13:01:31'),(2,'1901831','2021-2022','UPI','2022-11-16 00:00:00','OK',340.000,'CR','SM3','OK','REF234','2022-12-19 13:01:31');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu` (
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

LOCK TABLES `role_menu` WRITE;
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL DEFAULT '0',
  `name` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_RESIDENT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_fee_receipt`
--

DROP TABLE IF EXISTS `student_fee_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_fee_receipt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `roll_number` varchar(10) DEFAULT NULL,
  `receipt_id` int DEFAULT NULL,
  `program_fee_dates_id` int DEFAULT NULL,
  `semester_code` varchar(3) DEFAULT NULL,
  `program_id` char(10) DEFAULT NULL,
  `semester_start_date` date DEFAULT NULL,
  `semester_end_date` date DEFAULT NULL,
  `remarks` varchar(45) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_receiptid_idx` (`receipt_id`),
  KEY `FK_PROGRAM_FEE_DATES_ID_idx` (`program_fee_dates_id`),
  CONSTRAINT `FK_PROGRAM_FEE_DATES_ID` FOREIGN KEY (`program_fee_dates_id`) REFERENCES `fee_dates` (`id`),
  CONSTRAINT `FK_RECEIPT_ID` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_fee_receipt`
--

LOCK TABLES `student_fee_receipt` WRITE;
/*!40000 ALTER TABLE `student_fee_receipt` DISABLE KEYS */;
INSERT INTO `student_fee_receipt` VALUES (1,'1901831',1,1,'SM7','0001067','2022-07-01','2022-12-31','OK','2022-12-21 15:13:07'),(2,'1901831',2,2,'SM3','0001067','2021-07-01','2021-12-31','OK','2022-12-21 15:13:07');
/*!40000 ALTER TABLE `student_fee_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_fee_status`
--

DROP TABLE IF EXISTS `student_fee_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_fee_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `program_course_key` varchar(12) DEFAULT NULL,
  `roll_number` char(10) DEFAULT NULL,
  `semester_start_date` date DEFAULT NULL,
  `semester_end_date` date DEFAULT NULL,
  `entry_type` char(2) DEFAULT NULL COMMENT 'CR or DB',
  `bankrefno` varchar(45) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_fee_status`
--

LOCK TABLES `student_fee_status` WRITE;
/*!40000 ALTER TABLE `student_fee_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_fee_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  `default_role` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL DEFAULT '0',
  `password` varchar(120) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'$2a$12$Mf1JALTA5PMJOo.TlWnrWebn86ureGltXAVxTau2Mu.TybH.HCG6W','arush');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-23 16:33:34
