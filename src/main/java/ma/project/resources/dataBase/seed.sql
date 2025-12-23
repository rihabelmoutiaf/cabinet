INSERT INTO roles (nom, description, type_role, permissions) VALUES
('Super Administrateur', 'Accès complet au système', 'ADMIN',
 '["MANAGE_USERS", "MANAGE_ROLES", "MANAGE_CABINET", "VIEW_STATISTICS", "MANAGE_PATIENTS", "MANAGE_APPOINTMENTS", "MANAGE_FINANCES", "DELETE_USERS", "SYSTEM_SETTINGS"]'),

('Administrateur', 'Gestion administrative du cabinet', 'ADMIN',
 '["MANAGE_USERS", "MANAGE_ROLES", "MANAGE_CABINET", "VIEW_STATISTICS", "MANAGE_PATIENTS", "MANAGE_APPOINTMENTS", "MANAGE_FINANCES"]'),

('Médecin', 'Gestion médicale des patients', 'MEDECIN',
 '["MANAGE_PATIENTS", "MANAGE_APPOINTMENTS", "VIEW_MEDICAL_RECORDS", "CREATE_PRESCRIPTIONS", "VIEW_STATISTICS", "CREATE_CONSULTATIONS"]'),

('Secrétaire', 'Gestion administrative et accueil', 'SECRETAIRE',
 '["MANAGE_APPOINTMENTS", "VIEW_PATIENTS", "MANAGE_FINANCES", "VIEW_AGENDA"]'),

('Personnel', 'Accès limité', 'STAFF',
 '["VIEW_APPOINTMENTS", "VIEW_PATIENTS"]');

-- Insertion d'un super admin par défaut
-- Mot de passe: Admin@123 (hash BCrypt)
INSERT INTO utilisateurs (type_user, nom, prenom, email, tel, login, mot_de_passe, role_id, actif, premier_connexion)
VALUES ('ADMIN', 'ADMIN', 'Système', 'admin@dentaltech.ma', '0600000000', 'admin',
        '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyYb8dHxCK1u',
        1, TRUE, FALSE);

-- Ajouter les infos admin
INSERT INTO admins (utilisateur_id, niveau, peut_creer_utilisateurs, peut_supprimer_utilisateurs, peut_gerer_cabinet, modules_acces)
VALUES (1, 'Super Admin', TRUE, TRUE, TRUE,
        '["GESTION_UTILISATEURS", "GESTION_CABINET", "GESTION_ROLES", "STATISTIQUES", "PARAMETRAGE", "SAUVEGARDE"]');

-- Insertion d'un cabinet médical par défaut
INSERT INTO cabinet_medical (nom, email, adresse, tel1, description)
VALUES ('Cabinet Dentaire DentalTech', 'contact@dentaltech.ma',
        'Avenue Mohammed V, Casablanca', '0522000000',
        'Cabinet dentaire moderne équipé des dernières technologies');

-- Insertion de quelques actes dentaires courants
INSERT INTO actes (nom, description, type_acte, tarif_base) VALUES
('Consultation', 'Examen dentaire général', 'Consultation', 200.00),
('Détartrage', 'Nettoyage et détartrage dentaire', 'Prophylaxie', 300.00),
('Extraction simple', 'Extraction d''une dent simple', 'Chirurgie', 400.00),
('Plombage', 'Restauration dentaire composite', 'Conservation', 500.00),
('Couronne céramique', 'Pose d''une couronne en céramique', 'Prothèse', 2500.00),
('Blanchiment dentaire', 'Blanchiment professionnel', 'Esthétique', 1800.00),
('Radiographie', 'Radiographie panoramique', 'Imagerie', 150.00);

-- Insertion de quelques médicaments courants
INSERT INTO medicaments (nom, laboratoire, dosage, forme, description) VALUES
('Amoxicilline', 'Sanofi', '500mg', 'Gélule', 'Antibiotique à large spectre'),
('Ibuprofène', 'Pfizer', '400mg', 'Comprimé', 'Anti-inflammatoire et analgésique'),
('Paracétamol', 'Doliprane', '1000mg', 'Comprimé', 'Antalgique et antipyrétique'),
('Chlorhexidine', 'Colgate', '0.12%', 'Bain de bouche', 'Antiseptique buccal'),
('Métronidazole', 'Flagyl', '500mg', 'Comprimé', 'Antibiotique antiparasitaire');
