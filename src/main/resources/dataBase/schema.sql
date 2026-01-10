
CREATE DATABASE dentaldb;
USE dentaldb;


DROP TABLE IF EXISTS Revenues, Charges, SituationFinanciere, Facture, Certificat,
Prescription, Ordonnance, Consultation, InterventionMedecin, Acte, Medicament,
DossierMedical, FileAttente, AgendaPatient, RDV, AgendaMedecin, Utilisateur, CabinetMedical;

-- ============================================
-- TABLE CabinetMedical
-- ============================================
CREATE TABLE CabinetMedical (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    adresse TEXT,
    tel1 VARCHAR(20),
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE Utilisateur
-- ============================================
CREATE TABLE Utilisateur (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    roleType ENUM('ADMIN','MEDECIN','SECRETAIRE','PATIENT','STAFF') NOT NULL,
    password VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE AgendaMedecin
-- ============================================
CREATE TABLE AgendaMedecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medecinId BIGINT NOT NULL,
    date DATE NOT NULL,
    heureDebut TIME,
    heureFin TIME,
    disponible BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medecinId) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE RDV
-- ============================================
CREATE TABLE RDV (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    medecinId BIGINT NOT NULL,
    dateHeure TIMESTAMP NOT NULL,
    statut ENUM('PLANIFIE','CONFIRME','ANNULE','TERMINE') DEFAULT 'PLANIFIE',
    motif TEXT,
    noteRdvMedecin TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (medecinId) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE AgendaPatient
-- ============================================
CREATE TABLE AgendaPatient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    rdvId BIGINT NOT NULL,
    date DATE NOT NULL,
    heure TIME NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (rdvId) REFERENCES RDV(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE FileAttente
-- ============================================
CREATE TABLE FileAttente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    position INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE DossierMedical
-- ============================================
CREATE TABLE DossierMedical (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT UNIQUE NOT NULL,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notesInitiales TEXT,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE Acte
-- ============================================
CREATE TABLE Acte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    typeActe VARCHAR(100),
    tarifBase DECIMAL(10,2),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE InterventionMedecin
-- ============================================
CREATE TABLE InterventionMedecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    utilisateurId BIGINT NOT NULL,
    dateIntervention TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typeIntervention VARCHAR(100),
    description TEXT,
    prixDePatient DECIMAL(10,2),
    nombDelit INT,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id) ON DELETE CASCADE
);

-- ============================================
-- TABLE Consultation
-- ============================================
CREATE TABLE Consultation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    utilisateurId BIGINT NOT NULL,
    dateConsultation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    diagnostic TEXT,
    traitement TEXT,
    statut ENUM('PLANIFIE','CONFIRME','ANNULE','TERMINE'),
    observationMedecin TEXT,
    interventionMedecinId BIGINT,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    FOREIGN KEY (interventionMedecinId) REFERENCES InterventionMedecin(id)
);

-- ============================================
-- TABLE Medicament
-- ============================================
CREATE TABLE Medicament (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    laboratoire VARCHAR(255),
    dosage VARCHAR(100),
    presentation VARCHAR(100),
    typeDosage VARCHAR(50),
    forme VARCHAR(50),
    remboursable BOOLEAN DEFAULT FALSE,
    posologie TEXT,
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE Ordonnance
-- ============================================
CREATE TABLE Ordonnance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    utilisateurId BIGINT NOT NULL,
    consultationId BIGINT,
    dateOrdonnance TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id),
    FOREIGN KEY (consultationId) REFERENCES Consultation(id)
);

-- ============================================
-- TABLE Prescription
-- ============================================
CREATE TABLE Prescription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ordonnanceId BIGINT NOT NULL,
    medicamentId BIGINT NOT NULL,
    patientId BIGINT NOT NULL,
    utilisateurId BIGINT NOT NULL,
    datePrescription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    frequence INT,
    dureeEnJours INT,
    FOREIGN KEY (ordonnanceId) REFERENCES Ordonnance(id),
    FOREIGN KEY (medicamentId) REFERENCES Medicament(id),
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Certificat
-- ============================================
CREATE TABLE Certificat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    utilisateurId BIGINT NOT NULL,
    consultationId BIGINT,
    dateEmission TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typeCertificat VARCHAR(100),
    dureeEnJours INT,
    motif TEXT,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id),
    FOREIGN KEY (consultationId) REFERENCES Consultation(id)
);

-- ============================================
-- TABLE Facture
-- ============================================
CREATE TABLE Facture (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT NOT NULL,
    numeroFacture VARCHAR(50) UNIQUE,
    dateFacture TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montantTotal DECIMAL(10,2) NOT NULL,
    totalPaye DECIMAL(10,2) DEFAULT 0,
    reste DECIMAL(10,2),
    estPayee BOOLEAN DEFAULT FALSE,
    statut VARCHAR(50) DEFAULT 'En attente',
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE SituationFinanciere
-- ============================================
CREATE TABLE SituationFinanciere (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT UNIQUE NOT NULL,
    totalDesActes DECIMAL(10,2) DEFAULT 0,
    totalePaye DECIMAL(10,2) DEFAULT 0,
    credit DECIMAL(10,2) DEFAULT 0,
    statut ENUM('SAIN','EN_RETARD','CRITIQUE'),
    observation TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Charges
-- ============================================
CREATE TABLE Charges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    utilisateurId BIGINT,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Revenues
-- ============================================
CREATE TABLE Revenues (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    factureId BIGINT,
    utilisateurId BIGINT,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id),
    FOREIGN KEY (factureId) REFERENCES Facture(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);
-- ============================================
-- SUPPRESSION DES TABLES SI ELLES EXISTENT
-- ============================================
DROP TABLE IF EXISTS Revenues;
DROP TABLE IF EXISTS Charges;
DROP TABLE IF EXISTS SituationFinanciere;
DROP TABLE IF EXISTS Facture;
DROP TABLE IF EXISTS Certificat;
DROP TABLE IF EXISTS Prescription;
DROP TABLE IF EXISTS Ordonnance;
DROP TABLE IF EXISTS Consultation;
DROP TABLE IF EXISTS InterventionMedecin;
DROP TABLE IF EXISTS Acte;
DROP TABLE IF EXISTS Medicament;
DROP TABLE IF EXISTS DossierMedical;
DROP TABLE IF EXISTS FileAttente;
DROP TABLE IF EXISTS AgendaPatient;
DROP TABLE IF EXISTS RDV;
DROP TABLE IF EXISTS AgendaMedecin;
DROP TABLE IF EXISTS Antecedent;
DROP TABLE IF EXISTS Patient;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS CabinetMedical;

-- ============================================
-- TABLE CabinetMedical
-- ============================================
CREATE TABLE CabinetMedical (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    adresse TEXT,
    tel1 VARCHAR(20),
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE Utilisateur
-- ============================================
CREATE TABLE Utilisateur (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    roleType ENUM('ADMIN','MEDECIN','SECRETAIRE','STAFF') NOT NULL,
    password VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id)
);

-- ============================================
-- TABLE Patient
-- ============================================
CREATE TABLE Patient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    telephone VARCHAR(20),
    adresse TEXT,
    dateNaissance DATE,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE Antecedent
-- ============================================
CREATE TABLE Antecedent (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    nom VARCHAR(255) NOT NULL,
    categorie ENUM('MEDICAL','CHIRURGICAL','ALLERGIE','HABITUDE_VIE'),
    niveauRisque ENUM('FAIBLE','MODERE','ELEVE'),
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);

-- ============================================
-- TABLE AgendaMedecin
-- ============================================
CREATE TABLE AgendaMedecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medecinId BIGINT,
    date DATE NOT NULL,
    heureDebut TIME,
    heureFin TIME,
    disponible BOOLEAN DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medecinId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE RDV
-- ============================================
CREATE TABLE RDV (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    medecinId BIGINT,
    dateHeure TIMESTAMP NOT NULL,
    statut ENUM('PLANIFIE','ANNULE','TERMINE') DEFAULT 'PLANIFIE',
    motif TEXT,
    noteRdvMedecin TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (medecinId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE AgendaPatient
-- ============================================
CREATE TABLE AgendaPatient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    rdvId BIGINT,
    date DATE NOT NULL,
    heure TIME NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (rdvId) REFERENCES RDV(id)
);

-- ============================================
-- TABLE FileAttente
-- ============================================
CREATE TABLE FileAttente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    position INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);

-- ============================================
-- TABLE DossierMedical
-- ============================================
CREATE TABLE DossierMedical (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT UNIQUE,
    dateCreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    notesInitiales TEXT,
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);

-- ============================================
-- TABLE Acte
-- ============================================
CREATE TABLE Acte (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    typeActe VARCHAR(100),
    tarifBase DECIMAL(10,2),
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE InterventionMedecin
-- ============================================
CREATE TABLE InterventionMedecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    utilisateurId BIGINT,
    dateIntervention TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typeIntervention VARCHAR(100),
    description TEXT,
    prixDePatient DECIMAL(10,2),
    nombDelit INT,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Consultation
-- ============================================
CREATE TABLE Consultation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    utilisateurId BIGINT,
    dateConsultation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    diagnostic TEXT,
    traitement TEXT,
    statut ENUM('TERMINEE','EN_COURS','ANNULEE'),
    observationMedecin TEXT,
    interventionMedecinId BIGINT,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id),
    FOREIGN KEY (interventionMedecinId) REFERENCES InterventionMedecin(id)
);

-- ============================================
-- TABLE Medicament
-- ============================================
CREATE TABLE Medicament (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    laboratoire VARCHAR(255),
    dosage VARCHAR(100),
    presentation VARCHAR(100),
    typeDosage VARCHAR(50),
    forme VARCHAR(50),
    remboursable BOOLEAN DEFAULT FALSE,
    posologie TEXT,
    description TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE Ordonnance
-- ============================================
CREATE TABLE Ordonnance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    utilisateurId BIGINT,
    consultationId BIGINT,
    dateOrdonnance TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id),
    FOREIGN KEY (consultationId) REFERENCES Consultation(id)
);

-- ============================================
-- TABLE Prescription
-- ============================================
CREATE TABLE Prescription (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ordonnanceId BIGINT,
    medicamentId BIGINT,
    patientId BIGINT,
    utilisateurId BIGINT,
    datePrescription TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    frequence INT,
    dureeEnJours INT,
    FOREIGN KEY (ordonnanceId) REFERENCES Ordonnance(id),
    FOREIGN KEY (medicamentId) REFERENCES Medicament(id),
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Certificat
-- ============================================
CREATE TABLE Certificat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    utilisateurId BIGINT,
    consultationId BIGINT,
    dateEmission TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    typeCertificat VARCHAR(100),
    dureeEnJours INT,
    motif TEXT,
    FOREIGN KEY (patientId) REFERENCES Patient(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id),
    FOREIGN KEY (consultationId) REFERENCES Consultation(id)
);

-- ============================================
-- TABLE Facture
-- ============================================
CREATE TABLE Facture (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT,
    numeroFacture VARCHAR(50) UNIQUE,
    dateFacture TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montantTotal DECIMAL(10,2) NOT NULL,
    totalPaye DECIMAL(10,2) DEFAULT 0,
    reste DECIMAL(10,2),
    estPayee BOOLEAN DEFAULT FALSE,
    statut VARCHAR(50) DEFAULT 'En attente',
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);

-- ============================================
-- TABLE SituationFinanciere
-- ============================================
CREATE TABLE SituationFinanciere (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patientId BIGINT UNIQUE,
    totalDesActes DECIMAL(10,2) DEFAULT 0,
    totalePaye DECIMAL(10,2) DEFAULT 0,
    credit DECIMAL(10,2) DEFAULT 0,
    statut ENUM('SAIN','EN_RETARD','CRITIQUE') DEFAULT 'SAIN',
    observation TEXT,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (patientId) REFERENCES Patient(id)
);
-- ============================================
-- TABLE Charges
-- ============================================
CREATE TABLE Charges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    utilisateurId BIGINT,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE Revenues
-- ============================================
CREATE TABLE Revenues (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cabinetId BIGINT,
    factureId BIGINT,
    utilisateurId BIGINT,
    titre VARCHAR(255) NOT NULL,
    description TEXT,
    montant DECIMAL(10,2) NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cabinetId) REFERENCES CabinetMedical(id),
    FOREIGN KEY (factureId) REFERENCES Facture(id),
    FOREIGN KEY (utilisateurId) REFERENCES Utilisateur(id)
);
-- ============================================
-- SUPPRESSION DES TABLES SI ELLES EXISTENT
-- ============================================
DROP TABLE IF EXISTS Notification;
DROP TABLE IF EXISTS Secretaire;
DROP TABLE IF EXISTS Medecin;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Staff;

DROP TABLE IF EXISTS Role;

-- ============================================
-- TABLE ROLE
-- ============================================
CREATE TABLE Role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);

-- ============================================
-- TABLE UTILISATEUR (Base)
-- ============================================


-- ============================================
-- TABLE STAFF (hérite de Utilisateur)
-- ============================================
CREATE TABLE Staff (
    id BIGINT PRIMARY KEY,
    cin VARCHAR(50),
    salaire DECIMAL(10,2),
    prime DECIMAL(10,2),
    dateEmbauche DATE,
    dateDepart DATE,
    FOREIGN KEY (id) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE ADMIN (hérite de Utilisateur)
-- ============================================
CREATE TABLE Admin (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Utilisateur(id)
);

-- ============================================
-- TABLE MEDECIN (hérite de Staff)
-- ============================================
CREATE TABLE Medecin (
    id BIGINT PRIMARY KEY,
    specialite VARCHAR(100),
    agendaMensuel TEXT,
    FOREIGN KEY (id) REFERENCES Staff(id)
);

-- ============================================
-- TABLE SECRETAIRE (hérite de Staff)
-- ============================================
CREATE TABLE Secretaire (
    id BIGINT PRIMARY KEY,
    numCNSS VARCHAR(50),
    commission DECIMAL(10,2),
    FOREIGN KEY (id) REFERENCES Staff(id)
);

-- ============================================
-- TABLE NOTIFICATION
-- ============================================
CREATE TABLE Notification (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT,
    message TEXT NOT NULL,
    lue BOOLEAN DEFAULT FALSE,
    dateEnvoi TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES Utilisateur(id)
);
