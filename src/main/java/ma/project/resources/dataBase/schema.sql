-- ============================================
-- Script de création des tables pour DentalTech
-- Version compatible avec le code Java
-- ============================================

DROP DATABASE IF EXISTS dentaltech;
CREATE DATABASE dentaltech CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dentaltech;

-- ============================================================================
-- TABLE: roles
-- ============================================================================
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: utilisateurs
-- ============================================================================
CREATE TABLE utilisateurs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    
    -- Type d'utilisateur
    type_user VARCHAR(20) NOT NULL COMMENT 'ADMIN, MEDECIN, SECRETAIRE, STAFF',
    
    -- Informations personnelles
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    email VARCHAR(255) NOT NULL UNIQUE,
    adresse VARCHAR(255),
    tel VARCHAR(20),
    sexe ENUM('Homme', 'Femme', 'AUTRE'),
    login VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(255) NOT NULL,
    date_naissance DATE,
    date_derniere_connexion DATE,
    
    -- Gestion du compte
    role_id BIGINT NOT NULL,
    actif BOOLEAN DEFAULT TRUE,
    premier_connexion BOOLEAN DEFAULT TRUE,
    tentatives_echouees INT DEFAULT 0,
    date_verrouillage TIMESTAMP NULL,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (role_id) REFERENCES roles(id),
    
    INDEX idx_email (email),
    INDEX idx_login (login),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: patients
-- ============================================================================
CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    adresse VARCHAR(255),
    telephone VARCHAR(20),
    email VARCHAR(255),
    date_naissance DATE,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sexe ENUM('Homme', 'Femme', 'AUTRE') NOT NULL,
    assurance ENUM('CNOPS', 'CNSS', 'Autre', 'Aucune') DEFAULT 'Aucune',
    
    INDEX idx_telephone (telephone),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: antecedents
-- ============================================================================
CREATE TABLE antecedents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT,
    nom VARCHAR(255) NOT NULL,
    categorie ENUM('MEDICAL', 'CHIRURGICAL', 'ALLERGIE', 'HABITUDE_VIE') NOT NULL,
    niveau_risque ENUM('FAIBLE', 'MODERE', 'ELEVE') NOT NULL,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: Patient_Antecedents (Many-to-Many)
-- ============================================================================
CREATE TABLE Patient_Antecedents (
    patient_id BIGINT NOT NULL,
    antecedent_id BIGINT NOT NULL,
    PRIMARY KEY (patient_id, antecedent_id),
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (antecedent_id) REFERENCES antecedents(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: rdv
-- ============================================================================
CREATE TABLE rdv (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    dateHeure TIMESTAMP NOT NULL,
    motif TEXT,
    statut ENUM('PLANIFIE', 'ANNULE', 'TERMINE') DEFAULT 'PLANIFIE',
    note_rdv_medecin TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (medecin_id) REFERENCES utilisateurs(id),
    
    INDEX idx_patient_id (patient_id),
    INDEX idx_medecin_id (medecin_id),
    INDEX idx_dateHeure (dateHeure)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- Insertion des rôles par défaut
-- ============================================
INSERT INTO roles (nom, description) VALUES 
    ('ADMIN', 'Administrateur système avec tous les droits'),
    ('MEDECIN', 'Médecin dentiste'),
    ('SECRETAIRE', 'Secrétaire médicale'),
    ('STAFF', 'Personnel du cabinet')
ON DUPLICATE KEY UPDATE description = VALUES(description);

-- ============================================
-- Insertion d'un compte admin par défaut
-- Login: admin
-- Mot de passe: Admin@123
-- Hash BCrypt généré avec 12 rounds
-- ============================================
INSERT INTO utilisateurs (
    type_user, nom, prenom, email, login, mot_de_passe, role_id, actif
) 
SELECT 
    'ADMIN',
    'Admin',
    'Système',
    'admin@dentaltech.ma',
    'admin',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TupxcqqhLgCzXDg6q5SN5H5wOe3u',
    r.id,
    TRUE
FROM roles r 
WHERE r.nom = 'ADMIN'
ON DUPLICATE KEY UPDATE email = VALUES(email);

-- ============================================
-- Index pour améliorer les performances
-- ============================================
CREATE INDEX idx_patients_telephone ON patients(telephone);
CREATE INDEX idx_patients_email ON patients(email);
CREATE INDEX idx_rdv_patient ON rdv(patient_id);
CREATE INDEX idx_rdv_medecin ON rdv(medecin_id);
CREATE INDEX idx_rdv_date ON rdv(dateHeure);