package ma.project.dentalTech.repository.test;

import ma.project.dentalTech.configuration.SessionFactory;
import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.repository.modules.dossierMedical.impl.ConsultationRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TestRepo {

    public static void main(String[] args) {
        SessionFactory sessionFactory = SessionFactory.getInstance();
        Connection conn = null;

        try {
            // 1️⃣ Obtenir la connexion via SessionFactory (utilise db.properties)
            conn = sessionFactory.getConnection();
            System.out.println("✅ Connexion à la base de données établie avec succès !\n");

            // 2️⃣ Créer le repository
            ConsultationRepositoryImpl repo = new ConsultationRepositoryImpl(conn);

            // 3️⃣ Créer une consultation test
            System.out.println("📝 Création d'une consultation de test...");
            Consultation c = new Consultation();
            c.setPatientId(1L);
            c.setUtilisateurId(1L);
            c.setDateConsultation(LocalDateTime.now());
            c.setDiagnostic("Test diagnostic");
            c.setTraitement("Test traitement");
            c.setObservationMedecin("Test observation");

            repo.create(c);
            System.out.println("✅ Consultation créée avec ID = " + c.getId() + "\n");

            // 4️⃣ Lire toutes les consultations
            System.out.println("📖 Lecture de toutes les consultations...");
            List<Consultation> consultations = repo.findAll();
            System.out.println("Nombre de consultations trouvées: " + consultations.size());
            consultations.forEach(cons -> 
                System.out.println("  - ID: " + cons.getId() + ", Diagnostic: " + cons.getDiagnostic())
            );
            System.out.println();

            // 5️⃣ Mettre à jour la consultation
            System.out.println("🔄 Mise à jour de la consultation...");
            c.setDiagnostic("Diagnostic modifié");
            repo.update(c);
            System.out.println("✅ Consultation mise à jour.\n");

            // 6️⃣ Lire la consultation mise à jour
            System.out.println("📖 Vérification de la mise à jour...");
            List<Consultation> updatedConsultations = repo.findAll();
            updatedConsultations.forEach(cons -> {
                if (cons.getId().equals(c.getId())) {
                    System.out.println("  - ID: " + cons.getId() + ", Diagnostic: " + cons.getDiagnostic());
                }
            });
            System.out.println();

            // 7️⃣ Supprimer la consultation
            System.out.println("🗑️  Suppression de la consultation...");
            repo.delete(c);
            System.out.println("✅ Consultation supprimée.\n");

            System.out.println("✅ Test terminé avec succès !");

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermer la connexion proprement
            if (sessionFactory != null) {
                sessionFactory.closeConnection();
            }
        }
    }
}
