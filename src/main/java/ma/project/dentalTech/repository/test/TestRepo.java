package ma.project.dentalTech.repository.test;

import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.repository.modules.dossierMedical.impl.ConsultationRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;

public class TestRepo {

    public static void main(String[] args) {
        // 1️⃣ Connexion à la base
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dentaldb",
                "root",
                "")) {

            // 2️⃣ Créer le repository
            ConsultationRepositoryImpl repo = new ConsultationRepositoryImpl(conn);

            // 3️⃣ Créer une consultation test
            Consultation c = new Consultation();
            c.setPatientId(1L);
            c.setUtilisateurId(1L);
            c.setDateConsultation(LocalDateTime.now());
            c.setDiagnostic("Test diagnostic");
            c.setTraitement("Test traitement");
            c.setObservationMedecin("Test observation");

            repo.create(c);
            System.out.println("Consultation créée avec ID = " + c.getId());

            // 4️⃣ Lire toutes les consultations
            List<Consultation> consultations = repo.findAll();
            consultations.forEach(cons -> System.out.println(cons.getDiagnostic()));

            // 5️⃣ Mettre à jour la consultation
            c.setDiagnostic("Diagnostic modifié");
            repo.update(c);
            System.out.println("Consultation mise à jour.");

            // 6️⃣ Supprimer la consultation
            repo.delete(c);
            System.out.println("Consultation supprimée.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
