-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: mtu_student_system
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `mtustudent`
--

DROP TABLE IF EXISTS `mtustudent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mtustudent` (
  `StudentIDNum` int NOT NULL,
  `Name` varchar(45) NOT NULL,
  `DateOfBirth` date NOT NULL,
  PRIMARY KEY (`StudentIDNum`),
  UNIQUE KEY `StudentIDNum_UNIQUE` (`StudentIDNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mtustudent`
--

LOCK TABLES `mtustudent` WRITE;
/*!40000 ALTER TABLE `mtustudent` DISABLE KEYS */;
INSERT INTO `mtustudent` VALUES (1,'Brian Sheridan','2000-01-01'),(6,'Gianna Jameson','1996-07-26'),(10,'Ximena Ware','2002-03-11'),(11,'Nehemiah Owens','2003-11-16'),(12,'Makai Humphrey','2000-02-08'),(13,'Brenden Mcclain','1997-08-18'),(14,'Rory Holland','2001-04-04'),(15,'Harley Wheeler','2002-09-23'),(16,'Jamya Winters','2001-02-14'),(17,'Gauge Christensen','2003-07-08'),(18,'Anton Parsons','2004-12-31'),(20,'Nathaniel Charles','2002-11-17'),(21,'Kamari Yu','2003-05-19'),(22,'Leah Gamble','2001-08-13'),(23,'Karen Meadows','2002-03-27'),(24,'Maya Castro','2004-01-09'),(25,'Amaris Cook','2000-12-25'),(26,'Avery Hogan','2001-06-04'),(27,'Daniella Stanley','2002-10-11'),(28,'Jensen Kelley','2003-04-01'),(29,'Lincoln Rush','2000-08-22'),(36,'Roger O Neill','2000-01-01'),(40,'Test','1997-09-09'),(51,'TestTwo','2001-01-01');
/*!40000 ALTER TABLE `mtustudent` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-05 21:29:33
