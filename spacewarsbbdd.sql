-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: planetwars
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `battle_log`
--

DROP TABLE IF EXISTS `battle_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battle_log` (
  `planet_id` int NOT NULL,
  `num_battle` int NOT NULL,
  `num_line` int NOT NULL,
  `log_entry` text,
  PRIMARY KEY (`planet_id`,`num_battle`,`num_line`),
  CONSTRAINT `battle_log_ibfk_1` FOREIGN KEY (`planet_id`, `num_battle`) REFERENCES `battle_stats` (`planet_id`, `num_battle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle_log`
--

LOCK TABLES `battle_log` WRITE;
/*!40000 ALTER TABLE `battle_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `battle_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `battle_stats`
--

DROP TABLE IF EXISTS `battle_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battle_stats` (
  `planet_id` int NOT NULL,
  `num_battle` int NOT NULL,
  `resource_metal_acquired` int DEFAULT NULL,
  `resource_deuterion_acquired` int DEFAULT NULL,
  PRIMARY KEY (`planet_id`,`num_battle`),
  CONSTRAINT `battle_stats_ibfk_1` FOREIGN KEY (`planet_id`) REFERENCES `planet_stats` (`planet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `battle_stats`
--

LOCK TABLES `battle_stats` WRITE;
/*!40000 ALTER TABLE `battle_stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `battle_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enemy_army`
--

DROP TABLE IF EXISTS `enemy_army`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enemy_army` (
  `planet_id` int NOT NULL,
  `num_battle` int NOT NULL,
  `light_hunter_threat` int DEFAULT NULL,
  `light_hunter_destroyed` int DEFAULT NULL,
  `heavy_hunter_threat` int DEFAULT NULL,
  `heavy_hunter_destroyed` int DEFAULT NULL,
  `battleship_threat` int DEFAULT NULL,
  `battleship_destroyed` int DEFAULT NULL,
  `armored_ship_threat` int DEFAULT NULL,
  `armored_ship_destroyed` int DEFAULT NULL,
  PRIMARY KEY (`planet_id`,`num_battle`),
  CONSTRAINT `enemy_army_ibfk_1` FOREIGN KEY (`planet_id`) REFERENCES `planet_stats` (`planet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enemy_army`
--

LOCK TABLES `enemy_army` WRITE;
/*!40000 ALTER TABLE `enemy_army` DISABLE KEYS */;
INSERT INTO `enemy_army` VALUES (1,1,NULL,0,NULL,0,NULL,0,NULL,0);
/*!40000 ALTER TABLE `enemy_army` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planet_battle_army`
--

DROP TABLE IF EXISTS `planet_battle_army`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planet_battle_army` (
  `planet_id` int NOT NULL,
  `num_battle` int NOT NULL,
  `light_hunter_built` int DEFAULT NULL,
  `light_hunter_destroyed` int DEFAULT NULL,
  `heavy_hunter_built` int DEFAULT NULL,
  `heavy_hunter_destroyed` int DEFAULT NULL,
  `battleship_built` int DEFAULT NULL,
  `battleship_destroyed` int DEFAULT NULL,
  `armored_ship_built` int DEFAULT NULL,
  `armored_ship_destroyed` int DEFAULT NULL,
  PRIMARY KEY (`planet_id`,`num_battle`),
  CONSTRAINT `planet_battle_army_ibfk_1` FOREIGN KEY (`planet_id`) REFERENCES `planet_stats` (`planet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planet_battle_army`
--

LOCK TABLES `planet_battle_army` WRITE;
/*!40000 ALTER TABLE `planet_battle_army` DISABLE KEYS */;
INSERT INTO `planet_battle_army` VALUES (1,1,0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `planet_battle_army` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planet_battle_defense`
--

DROP TABLE IF EXISTS `planet_battle_defense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planet_battle_defense` (
  `planet_id` int NOT NULL,
  `num_battle` int NOT NULL,
  `missile_launcher_built` int NOT NULL DEFAULT '0',
  `missile_launcher_destroyed` int NOT NULL DEFAULT '0',
  `ion_cannon_built` int NOT NULL DEFAULT '0',
  `ion_cannon_destroyed` int NOT NULL DEFAULT '0',
  `plasma_cannon_built` int NOT NULL DEFAULT '0',
  `plasma_cannon_destroyed` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`planet_id`,`num_battle`),
  CONSTRAINT `planet_battle_defense_ibfk_1` FOREIGN KEY (`planet_id`) REFERENCES `planet_stats` (`planet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planet_battle_defense`
--

LOCK TABLES `planet_battle_defense` WRITE;
/*!40000 ALTER TABLE `planet_battle_defense` DISABLE KEYS */;
/*!40000 ALTER TABLE `planet_battle_defense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planet_stats`
--

DROP TABLE IF EXISTS `planet_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planet_stats` (
  `planet_id` int NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `resource_metal_amount` int DEFAULT NULL,
  `resource_deuterion_amount` int DEFAULT NULL,
  `technology_defense_level` int DEFAULT NULL,
  `technology_attack_level` int DEFAULT NULL,
  `battle_counter` int DEFAULT NULL,
  `missile_launcher_remaining` int DEFAULT NULL,
  `ion_cannon_remaining` int DEFAULT NULL,
  `plasma_cannon_remaining` int DEFAULT NULL,
  `light_hunter_remaining` int DEFAULT NULL,
  `heavy_hunter_remaining` int DEFAULT NULL,
  `battleship_remaining` int DEFAULT NULL,
  `armored_ship_remaining` int DEFAULT NULL,
  PRIMARY KEY (`planet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planet_stats`
--

LOCK TABLES `planet_stats` WRITE;
/*!40000 ALTER TABLE `planet_stats` DISABLE KEYS */;
INSERT INTO `planet_stats` VALUES (1,'PlanetName',47000,26750,0,0,0,1,1,1,1,2,1,1);
/*!40000 ALTER TABLE `planet_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'planetwars'
--

--
-- Dumping routines for database 'planetwars'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-18 16:49:52
