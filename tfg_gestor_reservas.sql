-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: tfg_gestor_reservas
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `t_attendee`
--

DROP TABLE IF EXISTS `t_attendee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_attendee` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `booking_id` varchar(36) NOT NULL,
  `type` varchar(70) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_attendee_user_id_idx` (`user_id`),
  KEY `fk_attendee_booking_id_idx` (`booking_id`),
  CONSTRAINT `fk_attendee_booking_id` FOREIGN KEY (`booking_id`) REFERENCES `t_booking` (`id`),
  CONSTRAINT `fk_attendee_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_attendee`
--

LOCK TABLES `t_attendee` WRITE;
/*!40000 ALTER TABLE `t_attendee` DISABLE KEYS */;
INSERT INTO `t_attendee` VALUES ('58858c45-b0b8-49d4-ae2d-51fa56eb2b04','50e239de-8805-49c0-a421-567970817479','b4163cb9-deed-4e1c-b98c-38462d5cbcac','REQUIRED'),('eba9cfb6-70d0-4225-93fa-7906331d2aff','a1a63e9c-82b3-4dc0-b5fb-676eebe991f9','b4163cb9-deed-4e1c-b98c-38462d5cbcac','REQUIRED'),('fb651434-cb65-45d9-b70b-b230eadb537c','5c5aacde-ffe9-47f6-8b27-c596adca6993','75b8d2b2-b8f8-41f6-80c6-fb44b55b6750','REQUIRED');
/*!40000 ALTER TABLE `t_attendee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_booking`
--

DROP TABLE IF EXISTS `t_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_booking` (
  `id` varchar(36) NOT NULL,
  `creator_id` varchar(45) NOT NULL,
  `organizer_id` varchar(36) NOT NULL,
  `resource_id` varchar(36) NOT NULL,
  `floor_id` varchar(36) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `duration` bigint NOT NULL,
  `check_in_date` datetime DEFAULT NULL,
  `check_out_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_booking_organizer_id_idx` (`organizer_id`),
  KEY `fk_booking_resource_id_idx` (`resource_id`),
  KEY `fk_booking_floor_id_idx` (`floor_id`),
  KEY `fk_booking_creator_id_idx` (`creator_id`),
  CONSTRAINT `fk_booking_creator_id` FOREIGN KEY (`creator_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_booking_floor_id` FOREIGN KEY (`floor_id`) REFERENCES `t_floor` (`id`),
  CONSTRAINT `fk_booking_organizer_id` FOREIGN KEY (`organizer_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `fk_booking_resource_id` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_booking`
--

LOCK TABLES `t_booking` WRITE;
/*!40000 ALTER TABLE `t_booking` DISABLE KEYS */;
INSERT INTO `t_booking` VALUES ('20353845-d9c7-43b1-bbf4-5e2ffa4542d5','36e54cee-72ea-4d68-aed6-6bb6f50ab23d','2ae0ddf5-46f6-4c04-8c16-8be59402ffa5','0a2c66ed-f771-4e40-9fba-153f1d66bfeb','0e81a468-401b-494c-a338-aa3b508c135f','2023-07-05 08:15:00','2023-07-05 21:45:00',48600000,NULL,NULL),('75b8d2b2-b8f8-41f6-80c6-fb44b55b6750','36e54cee-72ea-4d68-aed6-6bb6f50ab23d','d923ab53-c590-4aaf-a727-e4d3e16b073b','1f2e2402-8ea8-486f-b7ef-faff8e7a350a','0e81a468-401b-494c-a338-aa3b508c135f','2023-07-05 08:45:00','2023-07-05 10:45:00',7200000,NULL,NULL),('b4163cb9-deed-4e1c-b98c-38462d5cbcac','36e54cee-72ea-4d68-aed6-6bb6f50ab23d','5c5aacde-ffe9-47f6-8b27-c596adca6993','17e79693-7915-4abf-a71d-ec355defd891','0e81a468-401b-494c-a338-aa3b508c135f','2023-07-05 08:45:00','2023-07-05 10:45:00',7200000,NULL,NULL);
/*!40000 ALTER TABLE `t_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_building`
--

DROP TABLE IF EXISTS `t_building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_building` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `organization_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_building_org_id_idx` (`organization_id`),
  CONSTRAINT `fk_building_org_id` FOREIGN KEY (`organization_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_building`
--

LOCK TABLES `t_building` WRITE;
/*!40000 ALTER TABLE `t_building` DISABLE KEYS */;
INSERT INTO `t_building` VALUES ('5f81bb41-8a6e-409b-a558-700fd7a8e1d7','Edificio B','25f71ffc-93f5-4a37-be19-e27044190559'),('de1cc311-0804-4be6-8a07-f0a224357c3b','Edificio A','25f71ffc-93f5-4a37-be19-e27044190559');
/*!40000 ALTER TABLE `t_building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_floor`
--

DROP TABLE IF EXISTS `t_floor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_floor` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `building_id` varchar(36) NOT NULL,
  `floor_number` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_floor_number` (`floor_number`,`building_id`),
  KEY `fk_floor_building_id_idx` (`building_id`),
  CONSTRAINT `fk_floor_building_id` FOREIGN KEY (`building_id`) REFERENCES `t_building` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_floor`
--

LOCK TABLES `t_floor` WRITE;
/*!40000 ALTER TABLE `t_floor` DISABLE KEYS */;
INSERT INTO `t_floor` VALUES ('0e81a468-401b-494c-a338-aa3b508c135f','Planta 1','de1cc311-0804-4be6-8a07-f0a224357c3b',1),('eaecf0d1-b12e-4eae-aeb2-4a7316d74083','Planta 2','5f81bb41-8a6e-409b-a558-700fd7a8e1d7',2),('f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','Planta 1','5f81bb41-8a6e-409b-a558-700fd7a8e1d7',1);
/*!40000 ALTER TABLE `t_floor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_organization`
--

DROP TABLE IF EXISTS `t_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_organization` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_organization`
--

LOCK TABLES `t_organization` WRITE;
/*!40000 ALTER TABLE `t_organization` DISABLE KEYS */;
INSERT INTO `t_organization` VALUES ('25f71ffc-93f5-4a37-be19-e27044190559','Company');
/*!40000 ALTER TABLE `t_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_resource`
--

DROP TABLE IF EXISTS `t_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_resource` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `floor_id` varchar(36) NOT NULL,
  `organization_id` varchar(36) NOT NULL,
  `category` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_resource_org_id_idx` (`organization_id`),
  KEY `fk_resource_floor_id_idx` (`floor_id`),
  CONSTRAINT `fk_resource_floor_id` FOREIGN KEY (`floor_id`) REFERENCES `t_floor` (`id`),
  CONSTRAINT `fk_resource_org_id` FOREIGN KEY (`organization_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_resource`
--

LOCK TABLES `t_resource` WRITE;
/*!40000 ALTER TABLE `t_resource` DISABLE KEYS */;
INSERT INTO `t_resource` VALUES ('0a2c66ed-f771-4e40-9fba-153f1d66bfeb','PUESTO 21','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('0be674fa-c394-4512-b80a-c6c476ccf788','SALA 16','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('0cc17082-b79a-4337-a2dd-cea682397669','SALA 22','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('17e79693-7915-4abf-a71d-ec355defd891','SALA 4','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('18bf07d9-00f4-4269-9ae6-f1721a6171be','PUESTO 8','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('1db5569a-f346-4790-9697-502ffe19e5d3','PUESTO 18','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('1f2e2402-8ea8-486f-b7ef-faff8e7a350a','SALA 10','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('20feac83-81c9-46af-9152-c89178d3e0ea','SALA 1','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('2171c96b-8f93-4f28-b48b-866416b65cbc','SALA 17','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('23c80d4d-d33b-4bca-9fa7-6c22e95a08d7','PUESTO 9','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('2cae57d7-8060-4cdf-a5d3-2653787efe07','SALA 12','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('2cd3c198-f0f9-424f-a474-c1f669376870','PUESTO 2','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('3110936c-bab8-4ff7-942f-6b552acc2fb5','SALA 7','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('31ba66c6-836b-4a50-9fba-6c881a5ea7db','SALA 12','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('3246dad1-2954-4979-91fa-b2ca5346f28c','SALA 6','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('33ce5788-4ca9-468e-9794-8e9c07ac1ddc','PUESTO 20','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('38d5b2e7-e2f8-4570-8711-9cb3e053bba1','SALA 11','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('3aa9e05c-8e27-4d22-aba3-48cf7be53eab','SALA 19','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('3e80665d-eddf-48db-83fa-6429bdf52768','SALA 14','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('406b4a87-ba8c-454b-bcfc-3536ee894a94','SALA 15','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('4440f4a7-ab74-4303-b79e-76dafdf5de1b','PUESTO 23','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('47e51278-1082-4282-91c1-7b203fce1709','SALA 24','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('49e9247d-c7e8-4bb2-a59b-c6ac1ddcb1ce','SALA 4','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('4a056f29-e231-4af1-8d75-dbc27710ba66','SALA 5','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('521ccddb-fafb-4b25-9f4a-f91947673afb','SALA 10','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('532a9d91-615a-4dbb-b000-6538e9b5cd63','SALA 21','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('55b308b6-60fe-441f-af31-4de1056a0265','PUESTO 7','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('59bfc063-5f8d-4bb0-a38d-3e31bdb30167','SALA 10','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('5f4c96ef-2c45-4b8a-a7ce-3b218797c946','SALA 13','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('6202673f-5b93-4479-8696-404b6bfda943','PUESTO 2','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('65a91211-500c-4776-9cbf-ccb8dbad9091','SALA 6','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('6839d4c7-5029-4a80-a2d0-755982bcc570','SALA 20','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('6f6b745b-784b-4c5f-9394-1da7c1f826e3','SALA 5','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('70907827-393a-4bc8-a003-10200fda1077','PUESTO 25','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('74844b29-a3d0-4fc1-b643-f561da802d99','SALA 9','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('7adf5215-98cb-4c12-9d69-e4342878de4f','SALA 13','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('7cfb0c09-215a-4cc3-8c0a-922e5ede9f1d','PUESTO 22','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('7f3a96f4-8245-408e-80cc-90cdd3852973','PUESTO 28','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('8055589d-2508-4489-af08-ba44a988edcc','PUESTO 19','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('85ddcbd2-a13c-40f5-a6fc-5c7854828f3d','PUESTO 1','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('86bf98bf-35d2-419d-b04a-5ce9a5053de0','SALA 13','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('8a9c6cc6-d292-4c7b-aaec-9618aceeb133','SALA 11','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('8b9fdda5-20cc-4452-a643-f9d0c9a6d2ec','SALA 4','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('8c1ddad3-7395-40f5-8b39-17fe092553cd','PUESTO 16','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('9084e605-811c-4061-8fc5-cca392d70c4d','PUESTO 5','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('973713d3-5222-441d-9b20-367881a73405','SALA 27','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('999741eb-7a8e-4de4-9101-464ca353576d','SALA 15','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('a0b02c58-da77-4b9f-ad2a-41887f0d483e','SALA 9','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('a360c7e1-cd81-436e-9230-b8c3697473e0','PUESTO 17','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('a5afe487-5d64-4fd1-bb84-fb957dbf9529','PUESTO 20','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('a5edb8d3-2286-4840-8a22-a62b4b8fbe5b','PUESTO 12','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('a82d2d4c-60bb-44ce-bd15-ada8e3c06d3b','PUESTO 8','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('aae72fab-e362-4401-9af1-231300a8f545','PUESTO 14','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('b1ec967f-c2b0-4627-8497-6c83a323f2fa','PUESTO 23','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('b2d19cd0-e70a-4c03-b128-1d8728808f8f','SALA 14','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('b8b35030-0497-4d4d-994d-9b94ac08a1d0','SALA 22','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('bbc2206d-b927-408f-a8f2-5321b521fee9','SALA 7','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('c14f36f8-7dbf-4942-9b3d-e43960c60efc','SALA 19','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('c33eebc1-571c-421e-a597-2655dc971b05','SALA 24','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('c4068a3d-1361-4b17-a1df-499dad9de36b','PUESTO 23','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('d02d5814-2353-493b-906e-3c22bffe924d','SALA 18','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('d0b8c498-2e4d-448d-a7a4-a4eb1a9036e8','PUESTO 1','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('d169ab33-ad44-4bde-9e3d-1938e643278f','SALA 21','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('d1944770-8462-4502-8a1c-d438acafc5d3','PUESTO 3','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('d4c33e1c-ac79-4e69-87b0-ec4b2bfcda40','PUESTO 2','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('d5c9e956-9bea-40e9-a616-e70f23a44be8','PUESTO 3','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('d803c139-dc7f-4bf4-b365-545f1e21ab34','SALA 8','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('dbc8833f-e063-4ec8-be3e-708803d83ea9','PUESTO 3','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('e0b448e0-fff9-4954-bcf9-6e462362edde','PUESTO 24','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('e0f84bc5-640d-4742-b1e4-3468afa96cbc','PUESTO 16','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('e7b81c73-0d0b-42a0-8c62-0cb81c146567','PUESTO 17','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('f1be9b11-7228-47e9-844c-427006481063','PUESTO 6','f3dd8cbd-3329-4671-b9e4-92cf2199a6f6','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION'),('f28527ad-50ac-400d-8a97-12dbd1cbb17a','SALA 18','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('f5ad6beb-05f1-4fa4-9d28-ce3b2c9893c8','SALA 15','eaecf0d1-b12e-4eae-aeb2-4a7316d74083','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('f944c908-fadf-4e0a-a25c-c806427bac20','SALA 26','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','SPACE'),('ff547e16-e647-4838-b0ec-e45ceb7f5f7a','PUESTO 11','0e81a468-401b-494c-a338-aa3b508c135f','25f71ffc-93f5-4a37-be19-e27044190559','WORKSTATION');
/*!40000 ALTER TABLE `t_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_space`
--

DROP TABLE IF EXISTS `t_space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_space` (
  `id` varchar(36) NOT NULL,
  `capacity` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_space_resource_id` FOREIGN KEY (`id`) REFERENCES `t_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_space`
--

LOCK TABLES `t_space` WRITE;
/*!40000 ALTER TABLE `t_space` DISABLE KEYS */;
INSERT INTO `t_space` VALUES ('0be674fa-c394-4512-b80a-c6c476ccf788',5),('0cc17082-b79a-4337-a2dd-cea682397669',15),('17e79693-7915-4abf-a71d-ec355defd891',20),('1f2e2402-8ea8-486f-b7ef-faff8e7a350a',8),('20feac83-81c9-46af-9152-c89178d3e0ea',5),('2171c96b-8f93-4f28-b48b-866416b65cbc',30),('2cae57d7-8060-4cdf-a5d3-2653787efe07',12),('3110936c-bab8-4ff7-942f-6b552acc2fb5',10),('31ba66c6-836b-4a50-9fba-6c881a5ea7db',4),('3246dad1-2954-4979-91fa-b2ca5346f28c',4),('38d5b2e7-e2f8-4570-8711-9cb3e053bba1',10),('3aa9e05c-8e27-4d22-aba3-48cf7be53eab',15),('3e80665d-eddf-48db-83fa-6429bdf52768',20),('406b4a87-ba8c-454b-bcfc-3536ee894a94',5),('47e51278-1082-4282-91c1-7b203fce1709',5),('49e9247d-c7e8-4bb2-a59b-c6ac1ddcb1ce',20),('4a056f29-e231-4af1-8d75-dbc27710ba66',25),('521ccddb-fafb-4b25-9f4a-f91947673afb',30),('532a9d91-615a-4dbb-b000-6538e9b5cd63',12),('59bfc063-5f8d-4bb0-a38d-3e31bdb30167',14),('5f4c96ef-2c45-4b8a-a7ce-3b218797c946',14),('65a91211-500c-4776-9cbf-ccb8dbad9091',14),('6839d4c7-5029-4a80-a2d0-755982bcc570',25),('6f6b745b-784b-4c5f-9394-1da7c1f826e3',25),('74844b29-a3d0-4fc1-b643-f561da802d99',30),('7adf5215-98cb-4c12-9d69-e4342878de4f',10),('86bf98bf-35d2-419d-b04a-5ce9a5053de0',10),('8a9c6cc6-d292-4c7b-aaec-9618aceeb133',15),('8b9fdda5-20cc-4452-a643-f9d0c9a6d2ec',20),('973713d3-5222-441d-9b20-367881a73405',20),('999741eb-7a8e-4de4-9101-464ca353576d',5),('a0b02c58-da77-4b9f-ad2a-41887f0d483e',5),('b2d19cd0-e70a-4c03-b128-1d8728808f8f',5),('b8b35030-0497-4d4d-994d-9b94ac08a1d0',5),('bbc2206d-b927-408f-a8f2-5321b521fee9',5),('c14f36f8-7dbf-4942-9b3d-e43960c60efc',5),('c33eebc1-571c-421e-a597-2655dc971b05',5),('d02d5814-2353-493b-906e-3c22bffe924d',5),('d169ab33-ad44-4bde-9e3d-1938e643278f',5),('d803c139-dc7f-4bf4-b365-545f1e21ab34',5),('f28527ad-50ac-400d-8a97-12dbd1cbb17a',5),('f5ad6beb-05f1-4fa4-9d28-ce3b2c9893c8',5),('f944c908-fadf-4e0a-a25c-c806427bac20',5);
/*!40000 ALTER TABLE `t_space` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_space_booking`
--

DROP TABLE IF EXISTS `t_space_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_space_booking` (
  `id` varchar(36) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_space_booking_id` FOREIGN KEY (`id`) REFERENCES `t_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_space_booking`
--

LOCK TABLES `t_space_booking` WRITE;
/*!40000 ALTER TABLE `t_space_booking` DISABLE KEYS */;
INSERT INTO `t_space_booking` VALUES ('75b8d2b2-b8f8-41f6-80c6-fb44b55b6750','Test','<p>Test</p>'),('b4163cb9-deed-4e1c-b98c-38462d5cbcac','test','<p>test</p>');
/*!40000 ALTER TABLE `t_space_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `organization_id` varchar(36) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_user_org_id_idx` (`organization_id`),
  CONSTRAINT `fk_user_org_id` FOREIGN KEY (`organization_id`) REFERENCES `t_organization` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES ('2ae0ddf5-46f6-4c04-8c16-8be59402ffa5','John','Doe','john.doe@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$BNUT3XoY7FBXgiXqqr0eWOnXct5JzoDqs.I2Xk9hvbFvt1rH/S/UO'),('36e54cee-72ea-4d68-aed6-6bb6f50ab23d','Aitor','Gómez','aitor.gomez@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('44707bde-c609-4fac-bbeb-6f9d522e72c0','User','Demo9','user.demo9@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('50e239de-8805-49c0-a421-567970817479','User','Demo8','user.demo8@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('5c5aacde-ffe9-47f6-8b27-c596adca6993','User','Demo5','user.demo5@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('7457819e-3045-401a-9519-72f7fb9edfab','User','Demo10','user.demo10@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('92594d34-bf8e-4c01-ac57-239b17fc12ea','User','Demo2','user.demo2@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('95c34f85-a7e5-4358-8e64-5a6cee13c74c','User','Demo4','user.demo4@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('a004610a-4a9f-44e8-b359-695740f08b35','User','Demo3','user.demo3@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('a1a63e9c-82b3-4dc0-b5fb-676eebe991f9','User','Demo1','user.demo1@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('ab4e6e2c-7cad-4cfe-94d2-5178f9df651b','User','Demo6','user.demo6@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm'),('d923ab53-c590-4aaf-a727-e4d3e16b073b','User','Demo7','user.demo7@demo.com','25f71ffc-93f5-4a37-be19-e27044190559','$2a$11$RIP./053KasweBWYrZoZ8.wbCjxq5HswehgEOMzxKDSE5jkFxWVCm');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workstation`
--

DROP TABLE IF EXISTS `t_workstation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workstation` (
  `id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_workstation_resource_id` FOREIGN KEY (`id`) REFERENCES `t_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workstation`
--

LOCK TABLES `t_workstation` WRITE;
/*!40000 ALTER TABLE `t_workstation` DISABLE KEYS */;
INSERT INTO `t_workstation` VALUES ('0a2c66ed-f771-4e40-9fba-153f1d66bfeb'),('18bf07d9-00f4-4269-9ae6-f1721a6171be'),('1db5569a-f346-4790-9697-502ffe19e5d3'),('23c80d4d-d33b-4bca-9fa7-6c22e95a08d7'),('2cd3c198-f0f9-424f-a474-c1f669376870'),('33ce5788-4ca9-468e-9794-8e9c07ac1ddc'),('4440f4a7-ab74-4303-b79e-76dafdf5de1b'),('55b308b6-60fe-441f-af31-4de1056a0265'),('6202673f-5b93-4479-8696-404b6bfda943'),('70907827-393a-4bc8-a003-10200fda1077'),('7cfb0c09-215a-4cc3-8c0a-922e5ede9f1d'),('7f3a96f4-8245-408e-80cc-90cdd3852973'),('8055589d-2508-4489-af08-ba44a988edcc'),('85ddcbd2-a13c-40f5-a6fc-5c7854828f3d'),('8c1ddad3-7395-40f5-8b39-17fe092553cd'),('9084e605-811c-4061-8fc5-cca392d70c4d'),('a360c7e1-cd81-436e-9230-b8c3697473e0'),('a5afe487-5d64-4fd1-bb84-fb957dbf9529'),('a5edb8d3-2286-4840-8a22-a62b4b8fbe5b'),('a82d2d4c-60bb-44ce-bd15-ada8e3c06d3b'),('aae72fab-e362-4401-9af1-231300a8f545'),('b1ec967f-c2b0-4627-8497-6c83a323f2fa'),('c4068a3d-1361-4b17-a1df-499dad9de36b'),('d0b8c498-2e4d-448d-a7a4-a4eb1a9036e8'),('d1944770-8462-4502-8a1c-d438acafc5d3'),('d4c33e1c-ac79-4e69-87b0-ec4b2bfcda40'),('d5c9e956-9bea-40e9-a616-e70f23a44be8'),('dbc8833f-e063-4ec8-be3e-708803d83ea9'),('e0b448e0-fff9-4954-bcf9-6e462362edde'),('e0f84bc5-640d-4742-b1e4-3468afa96cbc'),('e7b81c73-0d0b-42a0-8c62-0cb81c146567'),('f1be9b11-7228-47e9-844c-427006481063'),('ff547e16-e647-4838-b0ec-e45ceb7f5f7a');
/*!40000 ALTER TABLE `t_workstation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_workstation_booking`
--

DROP TABLE IF EXISTS `t_workstation_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_workstation_booking` (
  `id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_workstation_booking_id` FOREIGN KEY (`id`) REFERENCES `t_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_workstation_booking`
--

LOCK TABLES `t_workstation_booking` WRITE;
/*!40000 ALTER TABLE `t_workstation_booking` DISABLE KEYS */;
INSERT INTO `t_workstation_booking` VALUES ('20353845-d9c7-43b1-bbf4-5e2ffa4542d5');
/*!40000 ALTER TABLE `t_workstation_booking` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-05 15:09:52
