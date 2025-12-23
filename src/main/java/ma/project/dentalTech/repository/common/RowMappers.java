package ma.project.dentalTech.repository.common;

import ma.project.dentalTech.entities.agenda.RDV;
import ma.project.dentalTech.entities.enums.*;
import ma.project.dentalTech.entities.patient.Antecedent;
import ma.project.dentalTech.entities.patient.Patient;
import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.entities.users.Utilisateur;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Classe utilitaire pour mapper les ResultSet SQL vers les entités
 */
public class RowMappers {

    /**
     * Mappe un ResultSet vers un objet Patient
     */
    public static Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        p.setNom(rs.getString("nom"));
        p.setPrenom(rs.getString("prenom"));
        p.setAdresse(rs.getString("adresse"));
        p.setTelephone(rs.getString("telephone"));
        p.setEmail(rs.getString("email"));

        if (rs.getDate("dateNaissance") != null) {
            p.setDateNaissance(rs.getDate("dateNaissance").toLocalDate());
        }

        Timestamp ts = rs.getTimestamp("dateCreation");
        if (ts != null) {
            p.setDateCreation(ts.toLocalDateTime());
        }

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            p.setSexe(Sexe.valueOf(sexeStr));
        }

        String assuranceStr = rs.getString("assurance");
        if (assuranceStr != null) {
            p.setAssurance(Assurance.valueOf(assuranceStr));
        }

        return p;
    }

    /**
     * Mappe un ResultSet vers un objet Antecedent
     */
    public static Antecedent mapAntecedent(ResultSet rs) throws SQLException {
        Antecedent a = new Antecedent();
        a.setId(rs.getLong("id"));
        a.setPatientId(rs.getLong("patient_id"));
        a.setNom(rs.getString("nom"));

        String categorieStr = rs.getString("categorie");
        if (categorieStr != null) {
            a.setCategorie(CategorieAntecedent.valueOf(categorieStr));
        }

        String niveauStr = rs.getString("niveauRisque");
        if (niveauStr != null) {
            a.setNiveauRisque(NiveauRisque.valueOf(niveauStr));
        }

        return a;
    }

    /**
     * Mappe un ResultSet vers un objet RDV
     */
    public static RDV mapRDV(ResultSet rs) throws SQLException {
        RDV rdv = new RDV();
        rdv.setId(rs.getLong("id"));
        rdv.setPatientId(rs.getLong("patient_id"));
        rdv.setMedecinId(rs.getLong("medecin_id"));

        Timestamp ts = rs.getTimestamp("dateHeure");
        if (ts != null) {
            rdv.setDateHeure(ts.toLocalDateTime());
        }

        rdv.setMotif(rs.getString("motif"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            rdv.setStatut(StatutRDV.valueOf(statutStr));
        }

        rdv.setNoteRdvMedecin(rs.getString("noteRdvMedecin"));

        return rdv;
    }

    /**
     * Mappe un ResultSet vers un objet Role
     */
    public static Role mapRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setNom(rs.getString("nom"));
        role.setDescription(rs.getString("description"));
        return role;
    }

    /**
     * Mappe un ResultSet vers un objet Utilisateur (générique)
     * Note: Pour les classes concrètes (Admin, Medecin, etc.),
     * utilisez les mappers spécifiques
     */
    public static Utilisateur mapUtilisateur(ResultSet rs) throws SQLException {
        // Création d'une instance anonyme pour les cas génériques
        Utilisateur u = new Utilisateur() {};

        u.setId(rs.getLong("id"));
        u.setNom(rs.getString("nom"));
        u.setPrenom(rs.getString("prenom"));
        u.setEmail(rs.getString("email"));
        u.setAdresse(rs.getString("adresse"));
        u.setTel(rs.getString("tel"));
        u.setLogin(rs.getString("login"));
        u.setMotDePasse(rs.getString("motDePasse"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null && !sexeStr.isEmpty()) {
            u.setSexe(Sexe.valueOf(sexeStr));
        }

        if (rs.getDate("dateNaissance") != null) {
            u.setDateNaissance(rs.getDate("dateNaissance").toLocalDate());
        }

        if (rs.getDate("dateDerniereConnexion") != null) {
            u.setDateDerniereConnexion(rs.getDate("dateDerniereConnexion").toLocalDate());
        }

        Timestamp createdAt = rs.getTimestamp("createdAt");
        if (createdAt != null) {
            u.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updatedAt");
        if (updatedAt != null) {
            u.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        u.setActive(rs.getBoolean("active"));
        u.setRoleId(rs.getLong("roleId"));

        return u;
    }

    /**
     * Mappe un ResultSet vers un objet Admin
     */
    public static Admin mapAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();

        admin.setId(rs.getLong("id"));
        admin.setNom(rs.getString("nom"));
        admin.setPrenom(rs.getString("prenom"));
        admin.setEmail(rs.getString("email"));
        admin.setAdresse(rs.getString("adresse"));
        admin.setTel(rs.getString("tel"));
        admin.setLogin(rs.getString("login"));
        admin.setMotDePasse(rs.getString("motDePasse"));

        String sexeStr = rs.getString("sexe");
        if (sexeStr != null && !sexeStr.isEmpty()) {
            admin.setSexe(Sexe.valueOf(sexeStr));
        }

        if (rs.getDate("dateNaissance") != null) {
            admin.setDateNaissance(rs.getDate("dateNaissance").toLocalDate());
        }

        if (rs.getDate("dateDerniereConnexion") != null) {
            admin.setDateDerniereConnexion(rs.getDate("dateDerniereConnexion").toLocalDate());
        }

        Timestamp createdAt = rs.getTimestamp("createdAt");
        if (createdAt != null) {
            admin.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updatedAt");
        if (updatedAt != null) {
            admin.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        admin.setActive(rs.getBoolean("active"));
        admin.setRoleId(rs.getLong("roleId"));

        return admin;
    }
}