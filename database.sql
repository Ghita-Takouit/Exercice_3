-- Script SQL pour créer manuellement la base de données
-- Note: Hibernate créera automatiquement les tables avec hbm2ddl.auto=update

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS mariage_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Utiliser la base de données
USE mariage_db;

-- Les tables seront créées automatiquement par Hibernate
-- Voici le schéma pour référence :

/*
CREATE TABLE homme (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    date_naissance DATE NOT NULL
);

CREATE TABLE femme (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    date_naissance DATE NOT NULL
);

CREATE TABLE mariage (
    homme_id INT NOT NULL,
    femme_id INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE,
    nombre_enfants INT DEFAULT 0,
    PRIMARY KEY (homme_id, femme_id, date_debut),
    FOREIGN KEY (homme_id) REFERENCES homme(id) ON DELETE CASCADE,
    FOREIGN KEY (femme_id) REFERENCES femme(id) ON DELETE CASCADE
);
*/

-- Créer un utilisateur (optionnel)
-- CREATE USER IF NOT EXISTS 'mariage_user'@'localhost' IDENTIFIED BY 'password';
-- GRANT ALL PRIVILEGES ON mariage_db.* TO 'mariage_user'@'localhost';
-- FLUSH PRIVILEGES;

