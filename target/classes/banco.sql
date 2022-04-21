
CREATE SCHEMA shorted

USE shorted

CREATE TABLE `link` (
  `id_link` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(500) NOT NULL,
  `code` varchar(8) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_link`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;