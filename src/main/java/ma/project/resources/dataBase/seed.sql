-- ============================================
-- INSERTION CABINET
-- ============================================
INSERT INTO CabinetMedical (nom, email, adresse, tel1, description)
VALUES
('Cabinet Dentaire Sourire', 'contact@sourire.ma', 'Rue Hassan II, Rabat', '0537123456', 'Cabinet moderne');

-- ============================================
-- INSERTION UTILISATEURS
-- ============================================
INSERT INTO Utilisateur (cabinetId, nom, prenom, email, roleType, password, active)
VALUES
(1, 'Admin', 'Systeme', 'admin@sourire.ma', 'ADMIN', 'password123', TRUE),
(1, 'Bennani', 'Fatima', 'dr.bennani@sourire.ma', 'MEDECIN', 'password123', TRUE),
(1, 'Tazi', 'Karim', 'dr.tazi@sourire.ma', 'MEDECIN', 'password123', TRUE),
(1, 'Idrissi', 'Laila', 'secretaire@sourire.ma', 'SECRETAIRE', 'password123', TRUE),
(1, 'Amrani', 'Youssef', 'youssef.amrani@email.com', 'PATIENT', NULL, TRUE),
(1, 'Chakir', 'Samira', 'samira.chakir@email.com', 'PATIENT', NULL, TRUE);

-- ============================================
-- INSERTION AGENDA MEDECINS
-- ============================================
INSERT INTO AgendaMedecin (medecinId, date, heureDebut, heureFin)
VALUES
(2, '2025-01-15', '09:00:00', '12:00:00'),
(2, '2025-01-15', '14:00:00', '18:00:00'),
(3, '2025-01-15', '09:00:00', '13:00:00');

-- ============================================
-- INSERTION RDV
-- ============================================
INSERT INTO RDV (patientId, medecinId, dateHeure, statut, motif)
VALUES
(5, 2, '2025-01-15 10:00:00', 'PLANIFIE', 'Contrôle dentaire'),
(6, 2, '2025-01-15 11:00:00', 'CONFIRME', 'Détartrage');

-- ============================================
-- INSERTION AGENDA PATIENT
-- ============================================
INSERT INTO AgendaPatient (patientId, rdvId, date, heure)
VALUES
(5, 1, '2025-01-15', '10:00:00'),
(6, 2, '2025-01-15', '11:00:00');

-- ============================================
-- INSERTION FILE D’ATTENTE
-- ============================================
INSERT INTO FileAttente (patientId, position)
VALUES
(5, 1),
(6, 2);

-- ============================================
-- INSERTION DOSSIER MEDICAL
-- ============================================
INSERT INTO DossierMedical (patientId, notesInitiales)
VALUES
(5, 'Patient régulier, historique dentaire normal'),
(6, 'Sensibilité dentaire, traitement en cours');

-- ============================================
-- INSERTION ACTES
-- ============================================
INSERT INTO Acte (nom, description, typeActe, tarifBase)
VALUES
('Consultation', 'Examen dentaire complet', 'Consultation', 200.00),
('Détartrage', 'Nettoyage des dents', 'Soins', 300.00),
('Extraction', 'Extraction dentaire simple', 'Chirurgie', 500.00);

-- ============================================
-- INSERTION INTERVENTION MEDECIN
-- ============================================
INSERT INTO InterventionMedecin (patientId, utilisateurId, typeIntervention, description, prixDePatient)
VALUES
(5, 2, 'Plombage', 'Plombage molaire supérieure droite', 400.00),
(6, 2, 'Détartrage', 'Détartrage complet', 300.00);

-- ============================================
-- INSERTION CONSULTATION
-- ============================================
INSERT INTO Consultation (patientId, utilisateurId, diagnostic, traitement, statut, interventionMedecinId)
VALUES
(5, 2, 'Carie dentaire sur molaire', 'Plombage nécessaire', 'TERMINE', 1),
(6, 2, 'Tartre important', 'Détartrage effectué', 'TERMINE', 2);

-- ============================================
-- INSERTION MEDICAMENT
-- ============================================
INSERT INTO Medicament (nom, laboratoire, dosage, forme, remboursable, posologie)
VALUES
('Paracétamol', 'Sanofi', '500mg', 'Comprimé', TRUE, '1 comprimé 3 fois par jour'),
('Ibuprofène', 'Pfizer', '400mg', 'Comprimé', TRUE, '1 comprimé 2 fois par jour');

-- ============================================
-- INSERTION ORDONNANCE
-- ============================================
INSERT INTO Ordonnance (patientId, utilisateurId, consultationId)
VALUES
(5, 2, 1),
(6, 2, 2);

-- ============================================
-- INSERTION PRESCRIPTION
-- ============================================
INSERT INTO Prescription (ordonnanceId, medicamentId, patientId, utilisateurId, description, frequence, dureeEnJours)
VALUES
(1, 1, 5, 2, 'Pour la douleur post-intervention', 3, 3),
(2, 2, 6, 2, 'Pour réduire l’inflammation', 2, 5);

-- ============================================
-- INSERTION FACTURE
-- ============================================
INSERT INTO Facture (patientId, numeroFacture, montantTotal, totalPaye, reste, estPayee, statut)
VALUES
(5, 'FACT-2025-001', 600.00, 600.00, 0.00, TRUE, 'Payée'),
(6, 'FACT-2025-002', 500.00, 200.00, 300.00, FALSE, 'En attente');

-- ============================================
-- INSERTION SITUATION FINANCIERE
-- ============================================
INSERT INTO SituationFinanciere (patientId, totalDesActes, totalePaye, credit, statut)
VALUES
(5, 600.00, 600.00, 0.00, 'SAIN'),
(6, 500.00, 200.00, 300.00, 'EN_RETARD');

-- ============================================
-- INSERTION CHARGES
-- ============================================
INSERT INTO Charges (cabinetId, utilisateurId, titre, description, montant)
VALUES
(1, 1, 'Loyer cabinet', 'Loyer mensuel janvier 2025', 8000.00),
(1, 1, 'Électricité', 'Facture électricité janvier', 1200.00);

-- ============================================
-- INSERTION REVENUES
-- ============================================
INSERT INTO Revenues (cabinetId, factureId, utilisateurId, titre, description, montant)
VALUES
(1, 1, 2, 'Consultation + Plombage', 'Patient Amrani Youssef', 600.00),
(1, 2, 2, 'Détartrage', 'Patient Chakir Samira (acompte)', 200.00);

-- ============================================
-- INSERTION CERTIFICAT
-- ============================================
INSERT INTO Certificat (patientId, utilisateurId, consultationId, typeCertificat, dureeEnJours, motif)
VALUES
(5, 2, 1, 'Certificat médical', 3, 'Repos suite intervention dentaire');
-- ============================================
-- INSERTIONS DE TEST
-- ============================================

-- CabinetMedical
INSERT INTO CabinetMedical (nom, email, adresse, tel1, description)
VALUES
('Cabinet Dentaire Sourire', 'contact@sourire.ma', 'Rue Hassan II, Rabat', '0537123456', 'Cabinet moderne');

-- Utilisateurs (Médecins et staff)
INSERT INTO Utilisateur (cabinetId, nom, prenom, email, roleType, password)
VALUES
(1, 'Admin', 'Systeme', 'admin@sourire.ma', 'ADMIN', 'admin123'),
(1, 'Fatima', 'Bennani', 'dr.bennani@sourire.ma', 'MEDECIN', 'pass123'),
(1, 'Karim', 'Tazi', 'dr.tazi@sourire.ma', 'MEDECIN', 'pass123'),
(1, 'Laila', 'Idrissi', 'secretaire@sourire.ma', 'STAFF', 'pass123');

-- Patients
INSERT INTO Patient (nom, prenom, email, telephone, adresse, dateNaissance)
VALUES
('Amrani', 'Youssef', 'youssef.amrani@email.com', '0601234567', 'Rabat', '1995-06-15'),
('Chakir', 'Samira', 'samira.chakir@email.com', '0612345678', 'Casablanca', '1998-03-20');

-- Antecedents
INSERT INTO Antecedent (patientId, nom, categorie, niveauRisque)
VALUES
(1, 'Diabète', 'MEDICAL', 'MODERE'),
(2, 'Allergie aux antibiotiques', 'ALLERGIE', 'ELEVE');

-- AgendaMedecin
INSERT INTO AgendaMedecin (medecinId, date, heureDebut, heureFin)
VALUES
(2, '2025-01-15', '09:00:00', '12:00:00'),
(3, '2025-01-15', '13:00:00', '17:00:00');

-- RDV
INSERT INTO RDV (patientId, medecinId, dateHeure, statut, motif)
VALUES
(1, 2, '2025-01-15 10:00:00', 'PLANIFIE', 'Contrôle dentaire'),
(2, 2, '2025-01-15 11:00:00', 'PLANIFIE', 'Détartrage');

-- AgendaPatient
INSERT INTO AgendaPatient (patientId, rdvId, date, heure)
VALUES
(1, 1, '2025-01-15', '10:00:00'),
(2, 2, '2025-01-15', '11:00:00');

-- FileAttente
INSERT INTO FileAttente (patientId, position)
VALUES
(1, 1),
(2, 2);

-- DossierMedical
INSERT INTO DossierMedical (patientId, notesInitiales)
VALUES
(1, 'Patient régulier, historique dentaire normal'),
(2, 'Sensibilité dentaire, traitement en cours');

-- Acte
INSERT INTO Acte (nom, description, typeActe, tarifBase)
VALUES
('Détartrage', 'Nettoyage complet des dents', 'PREVENTIF', 200.00),
('Extraction', 'Extraction dentaire simple', 'CHIRURGICAL', 500.00);

-- InterventionMedecin
INSERT INTO InterventionMedecin (patientId, utilisateurId, typeIntervention, description, prixDePatient)
VALUES
(1, 2, 'Détartrage', 'Nettoyage complet', 200.00),
(2, 2, 'Extraction', 'Extraction molaire', 500.00);

-- Consultation
INSERT INTO Consultation (patientId, utilisateurId, diagnostic, traitement, statut, interventionMedecinId)
VALUES
(1, 2, 'Dents propres', 'Brossage quotidien', 'TERMINEE', 1),
(2, 2, 'Molaire cariée', 'Extraction nécessaire', 'TERMINEE', 2);

-- Medicament
INSERT INTO Medicament (nom, laboratoire, dosage, presentation, typeDosage, forme)
VALUES
('Paracétamol', 'LabA', '500mg', 'Comprimé', 'Oral', 'Comprimé'),
('Amoxicilline', 'LabB', '250mg', 'Comprimé', 'Oral', 'Comprimé');

-- Ordonnance
INSERT INTO Ordonnance (patientId, utilisateurId, consultationId)
VALUES
(1, 2, 1),
(2, 2, 2);

-- Prescription
INSERT INTO Prescription (ordonnanceId, medicamentId, patientId, utilisateurId, frequence, dureeEnJours)
VALUES
(1, 1, 1, 2, 3, 5),
(2, 2, 2, 2, 2, 7);

-- Certificat
INSERT INTO Certificat (patientId, utilisateurId, consultationId, typeCertificat, dureeEnJours)
VALUES
(1, 2, 1, 'Travail', 3),
(2, 2, 2, 'Sport', 7);

-- Facture
INSERT INTO Facture (patientId, numeroFacture, montantTotal, totalPaye, reste)
VALUES
(1, 'FAC001', 200.00, 200.00, 0.00),
(2, 'FAC002', 500.00, 200.00, 300.00);

-- SituationFinanciere
INSERT INTO SituationFinanciere (patientId, totalDesActes, totalePaye, credit, statut)
VALUES
(1, 200.00, 200.00, 0.00, 'SAIN'),
(2, 500.00, 200.00, 0.00, 'EN_RETARD');

-- Charges
INSERT INTO Charges (cabinetId, utilisateurId, titre, montant)
VALUES
(1, 4, 'Achat matériel', 1000.00),
(1, 4, 'Entretien', 500.00);

-- Revenues
INSERT INTO Revenues (cabinetId, factureId, utilisateurId, titre, montant)
VALUES
(1, 1, 2, 'Paiement patient Youssef', 200.00),
(1, 2, 2, 'Paiement patient Samira', 200.00);
INSERT INTO Staff (id, cin, salaire, prime, dateEmbauche, dateDepart)
VALUES (4, 'DD112233', 4000.00, 150.00, '2022-05-01', NULL);

-- ============================================
-- INSERTION DANS LA TABLE ROLE
-- ============================================
INSERT INTO Role (nom, description) VALUES
('ADMIN', 'Administrateur du système'),
('MEDECIN', 'Médecin du cabinet'),
('SECRETAIRE', 'Secrétaire du cabinet'),
('STAFF', 'Personnel du cabinet');






-- ============================================

-- ============================================
-- INSERTION DANS LA TABLE SECRETAIRE
-- ============================================
INSERT INTO Secretaire (id, numCNSS, commission) VALUES
(4, 'CNSS12345', 200.00);

-- ============================================
-- INSERTION DANS LA TABLE NOTIFICATION
-- ============================================
INSERT INTO Notification (userId, message, lue) VALUES
(1, 'Bienvenue dans le système!', FALSE),
(2, 'Vous avez un nouveau RDV demain', FALSE),
(3, 'Votre agenda a été mis à jour', TRUE),
(4, 'Nouvelle tâche assignée', FALSE);
