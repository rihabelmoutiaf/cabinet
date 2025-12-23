DROP DATABASE IF EXISTS dentaltech;
CREATE DATABASE dentaltech CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dentaltech;

-- ============================================================================
-- TABLE: roles
-- Gestion des rôles et permissions
-- ============================================================================
CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    type_role VARCHAR(50) NOT NULL COMMENT 'ADMIN, MEDECIN, SECRETAIRE, STAFF',
    permissions JSON COMMENT 'Liste des permissions au format JSON',
    actif BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_type_role (type_role),
    INDEX idx_actif (actif)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: utilisateurs (table mère pour tous les utilisateurs)
-- ============================================================================
CREATE TABLE utilisateurs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- Type d'utilisateur (héritage)
    type_user VARCHAR(20) NOT NULL COMMENT 'ADMIN, MEDECIN, SECRETAIRE, STAFF',

    -- Informations personnelles
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    tel VARCHAR(20),
    adresse TEXT,
    sexe ENUM('Homme', 'Femme', 'AUTRE') DEFAULT 'AUTRE',
    date_naissance DATE,

    -- Authentification
    login VARCHAR(50) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL COMMENT 'Hash BCrypt du mot de passe',

    -- Gestion du compte
    actif BOOLEAN DEFAULT TRUE,
    premier_connexion BOOLEAN DEFAULT TRUE,
    date_derniere_connexion DATE,
    tentatives_echouees INT DEFAULT 0,
    date_verrouillage TIMESTAMP NULL,

    -- Relation avec role
    role_id BIGINT NOT NULL,

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT,

    INDEX idx_type_user (type_user),
    INDEX idx_email (email),
    INDEX idx_login (login),
    INDEX idx_actif (actif),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: staff (informations supplémentaires pour Staff)
-- ============================================================================
CREATE TABLE staff (
    utilisateur_id BIGINT PRIMARY KEY,

    -- Informations professionnelles
    cin VARCHAR(20) UNIQUE,
    salaire DECIMAL(10,2),
    prime DECIMAL(10,2),
    date_embauche DATE,
    date_depart DATE,

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: medecins (informations supplémentaires pour Médecins)
-- Hérite de staff
-- ============================================================================
CREATE TABLE medecins (
    utilisateur_id BIGINT PRIMARY KEY,

    -- Informations professionnelles spécifiques
    specialite VARCHAR(100),
    numero_ordre VARCHAR(50) UNIQUE COMMENT 'Numéro inscription Ordre des médecins',
    numero_inpe VARCHAR(50) UNIQUE COMMENT 'Identifiant National',
    diplomes JSON COMMENT 'Liste des diplômes au format JSON',
    signature VARCHAR(255) COMMENT 'Chemin vers signature numérique',

    -- Informations de cabinet
    annees_experience INT DEFAULT 0,
    langues_parles VARCHAR(255),

    -- Informations financières
    taux_horaire DECIMAL(10,2),
    pourcentage_consultation DECIMAL(5,2),

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,

    INDEX idx_specialite (specialite)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: secretaires (informations supplémentaires pour Secrétaires)
-- Hérite de staff
-- ============================================================================
CREATE TABLE secretaires (
    utilisateur_id BIGINT PRIMARY KEY,

    -- Informations spécifiques
    num_cnss VARCHAR(50),
    commission DECIMAL(10,2),

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: admins (informations supplémentaires pour Admins)
-- ============================================================================
CREATE TABLE admins (
    utilisateur_id BIGINT PRIMARY KEY,

    -- Niveau d'administration
    niveau VARCHAR(50) DEFAULT 'Admin Principal' COMMENT 'Super Admin, Admin Principal, Admin Secondaire',

    -- Permissions spéciales
    peut_creer_utilisateurs BOOLEAN DEFAULT TRUE,
    peut_supprimer_utilisateurs BOOLEAN DEFAULT FALSE,
    peut_gerer_cabinet BOOLEAN DEFAULT TRUE,

    -- Modules accessibles
    modules_acces JSON COMMENT 'Liste des modules au format JSON',

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: cabinet_medical
-- Informations du cabinet dentaire
-- ============================================================================
CREATE TABLE cabinet_medical (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- Informations de base
    nom VARCHAR(200) NOT NULL,
    email VARCHAR(150),
    logo VARCHAR(255),

    -- Coordonnées
    adresse TEXT,
    tel1 VARCHAR(20),
    tel2 VARCHAR(20),

    -- Présence en ligne
    site_web VARCHAR(255),
    instagram VARCHAR(100),
    facebook VARCHAR(100),

    -- Description
    description TEXT,

    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: patients
-- ============================================================================
CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- Informations personnelles
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE,
    telephone VARCHAR(20),
    adresse TEXT,
    date_naissance DATE,
    sexe ENUM('Homme', 'Femme', 'AUTRE') DEFAULT 'AUTRE',

    -- Assurance
    assurance ENUM('CNOPS', 'CNSS', 'Autre', 'Aucune') DEFAULT 'Aucune',

    -- Timestamps
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_nom_prenom (nom, prenom),
    INDEX idx_email (email),
    INDEX idx_telephone (telephone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: antecedents
-- ============================================================================
CREATE TABLE antecedents (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,

    nom VARCHAR(200) NOT NULL,
    categorie ENUM('MEDICAL', 'CHIRURGICAL', 'ALLERGIE', 'HABITUDE_VIE') NOT NULL,
    niveau_risque ENUM('FAIBLE', 'MODERE', 'ELEVE') DEFAULT 'FAIBLE',

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,

    INDEX idx_patient_id (patient_id),
    INDEX idx_categorie (categorie),
    INDEX idx_niveau_risque (niveau_risque)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: dossiers_medicaux
-- ============================================================================
CREATE TABLE dossiers_medicaux (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL UNIQUE,

    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notes_initiales TEXT,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: rdv (Rendez-vous)
-- ============================================================================
CREATE TABLE rdv (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,

    dateHeure TIMESTAMP NOT NULL,
    statut ENUM('PLANIFIE', 'ANNULE', 'TERMINE') DEFAULT 'PLANIFIE',

    motif TEXT,
    note_rdv_medecin TEXT,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (medecin_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,

    INDEX idx_patient_id (patient_id),
    INDEX idx_medecin_id (medecin_id),
    INDEX idx_dateHeure (dateHeure),
    INDEX idx_statut (statut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: consultations
-- ============================================================================
CREATE TABLE consultations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    utilisateur_id BIGINT NOT NULL COMMENT 'Médecin qui fait la consultation',

    date_consultation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    diagnostic TEXT,
    traitement TEXT,
    statut ENUM('TERMINEE', 'EN_COURS', 'ANNULEE') DEFAULT 'EN_COURS',
    observation_medecin TEXT,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,

    INDEX idx_patient_id (patient_id),
    INDEX idx_utilisateur_id (utilisateur_id),
    INDEX idx_date_consultation (date_consultation)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: actes
-- Catalogue des actes médicaux
-- ============================================================================
CREATE TABLE actes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    nom VARCHAR(200) NOT NULL,
    description TEXT,
    type_acte VARCHAR(100),
    tarif_base DECIMAL(10,2) NOT NULL,

    INDEX idx_type_acte (type_acte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: interventions_medecin
-- ============================================================================
CREATE TABLE interventions_medecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    utilisateur_id BIGINT NOT NULL COMMENT 'Médecin',
    consultation_id BIGINT,

    date_intervention TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_intervention VARCHAR(100),
    description TEXT,

    prix_de_patient DECIMAL(10,2),
    nomb_delit INT COMMENT 'Nombre de dents/séances',

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id) ON DELETE SET NULL,

    INDEX idx_patient_id (patient_id),
    INDEX idx_consultation_id (consultation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: medicaments
-- ============================================================================
CREATE TABLE medicaments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    nom VARCHAR(200) NOT NULL,
    laboratoire VARCHAR(200),
    dosage VARCHAR(50),
    presentation VARCHAR(100),
    type_dosage VARCHAR(50),
    forme VARCHAR(50) COMMENT 'Comprimé, Sirop, Gélule, etc.',
    remboursable BOOLEAN DEFAULT FALSE,
    posologie TEXT,
    description TEXT,

    INDEX idx_nom (nom),
    INDEX idx_laboratoire (laboratoire)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: ordonnances
-- ============================================================================
CREATE TABLE ordonnances (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    utilisateur_id BIGINT NOT NULL COMMENT 'Médecin prescripteur',
    consultation_id BIGINT,

    date_ordonnance TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id) ON DELETE SET NULL,

    INDEX idx_patient_id (patient_id),
    INDEX idx_consultation_id (consultation_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: prescriptions (lignes d'ordonnance)
-- ============================================================================
CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    ordonnance_id BIGINT NOT NULL,
    medicament_id BIGINT NOT NULL,

    description TEXT,
    frequence INT COMMENT 'Nombre de prises par jour',
    duree_en_jours INT,

    FOREIGN KEY (ordonnance_id) REFERENCES ordonnances(id) ON DELETE CASCADE,
    FOREIGN KEY (medicament_id) REFERENCES medicaments(id) ON DELETE CASCADE,

    INDEX idx_ordonnance_id (ordonnance_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: certificats
-- ============================================================================
CREATE TABLE certificats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    utilisateur_id BIGINT NOT NULL COMMENT 'Médecin',
    consultation_id BIGINT,

    date_emission TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_certificat VARCHAR(100),
    duree_en_jours INT,
    motif TEXT,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id) ON DELETE SET NULL,

    INDEX idx_patient_id (patient_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: factures
-- ============================================================================
CREATE TABLE factures (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,

    numero_facture VARCHAR(50) UNIQUE,
    date_facture TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montant_total DECIMAL(10,2) NOT NULL,
    total_paye DECIMAL(10,2) DEFAULT 0,
    reste DECIMAL(10,2) GENERATED ALWAYS AS (montant_total - total_paye) STORED,

    est_payee BOOLEAN DEFAULT FALSE,
    statut VARCHAR(50) DEFAULT 'En attente',

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,

    INDEX idx_patient_id (patient_id),
    INDEX idx_est_payee (est_payee),
    INDEX idx_numero_facture (numero_facture)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: situation_financiere
-- ============================================================================
CREATE TABLE situation_financiere (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL UNIQUE,

    total_des_actes DECIMAL(10,2) DEFAULT 0,
    totale_paye DECIMAL(10,2) DEFAULT 0,
    credit DECIMAL(10,2) GENERATED ALWAYS AS (total_des_actes - totale_paye) STORED,

    statut ENUM('SOLDE_ACTUEL', 'AVANCE_PARTIELLE', 'NON_PAYE', 'EN_ATTENTE_VALIDATION')
           DEFAULT 'EN_ATTENTE_VALIDATION',
    observation TEXT,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,

    INDEX idx_statut (statut)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: charges (dépenses du cabinet)
-- ============================================================================
CREATE TABLE charges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    titre VARCHAR(200) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    utilisateur_id BIGINT COMMENT 'Qui a enregistré la charge',

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE SET NULL,

    INDEX idx_date (date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: revenues (revenus du cabinet)
-- ============================================================================
CREATE TABLE revenues (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    facture_id BIGINT,
    utilisateur_id BIGINT COMMENT 'Qui a enregistré le revenu',

    titre VARCHAR(200) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (facture_id) REFERENCES factures(id) ON DELETE SET NULL,
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE SET NULL,

    INDEX idx_date (date),
    INDEX idx_facture_id (facture_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: notifications
-- ============================================================================
CREATE TABLE notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    utilisateur_id BIGINT NOT NULL,

    message TEXT NOT NULL,
    type VARCHAR(50) COMMENT 'INFO, ALERTE, URGENT, SYSTEME',
    priorite VARCHAR(50) COMMENT 'BASSE, NORMALE, HAUTE, CRITIQUE',
    titre VARCHAR(200),

    lu BOOLEAN DEFAULT FALSE,
    date_envoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,

    INDEX idx_utilisateur_id (utilisateur_id),
    INDEX idx_lu (lu),
    INDEX idx_date_envoi (date_envoi)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: agenda_medecin
-- ============================================================================
CREATE TABLE agenda_medecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    medecin_id BIGINT NOT NULL,

    date DATE NOT NULL,
    heure_debut TIME,
    heure_fin TIME,
    disponible BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (medecin_id) REFERENCES utilisateurs(id) ON DELETE CASCADE,

    INDEX idx_medecin_date (medecin_id, date),
    INDEX idx_disponible (disponible)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================================
-- TABLE: file_attente
-- ============================================================================
CREATE TABLE file_attente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    patient_id BIGINT NOT NULL,
    position INT NOT NULL,

    date_ajout TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,

    INDEX idx_position (position),
    INDEX idx_date_ajout (date_ajout)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
