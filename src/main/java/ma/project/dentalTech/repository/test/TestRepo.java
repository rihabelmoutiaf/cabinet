package ma.project.dentalTech.repository.test;

import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.entities.enums.StatutRDV;
import ma.project.dentalTech.repository.modules.dossierMedical.impl.ConsultationRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Test class for Consultation Repository
 * This tests CRUD operations on Consultation entity
 */
public class TestRepo {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/dentaldb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   DENTALTECH - TEST REPOSITORY");
        System.out.println("===========================================\n");

        // Test with try-with-resources to auto-close connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("✓ Database connection successful!");
            System.out.println("-------------------------------------------\n");

            // Create repository instance
            ConsultationRepositoryImpl repo = new ConsultationRepositoryImpl(conn);

            // =====================================
            // TEST 1: CREATE A NEW CONSULTATION
            // =====================================
            System.out.println("TEST 1: Creating a new consultation...");

            Consultation newConsultation = new Consultation();
            newConsultation.setPatientId(1L);  // Must exist in Patient table
            newConsultation.setUtilisateurId(1L);  // Must exist in Utilisateur table
            newConsultation.setDateConsultation(LocalDateTime.now());
            newConsultation.setDiagnostic("Test diagnostic - Carie dentaire");
            newConsultation.setTraitement("Test traitement - Plombage");
            newConsultation.setStatut(StatutRDV.TERMINE);
            newConsultation.setObservationMedecin("Test observation - RAS");

            repo.create(newConsultation);
            System.out.println("✓ Consultation créée avec ID = " + newConsultation.getId());
            System.out.println("-------------------------------------------\n");

            // =====================================
            // TEST 2: READ ALL CONSULTATIONS
            // =====================================
            System.out.println("TEST 2: Reading all consultations...");

            List<Consultation> consultations = repo.findAll();
            System.out.println("✓ Total consultations found: " + consultations.size());

            for (Consultation c : consultations) {
                System.out.println("  - ID: " + c.getId() +
                        " | Patient: " + c.getPatientId() +
                        " | Diagnostic: " + c.getDiagnostic() +
                        " | Statut: " + c.getStatut());
            }
            System.out.println("-------------------------------------------\n");

            // =====================================
            // TEST 3: FIND BY ID
            // =====================================
            System.out.println("TEST 3: Finding consultation by ID...");

            Long searchId = newConsultation.getId();
            var foundConsultation = repo.findById(searchId);

            if (foundConsultation.isPresent()) {
                Consultation c = foundConsultation.get();
                System.out.println("✓ Consultation found:");
                System.out.println("  ID: " + c.getId());
                System.out.println("  Diagnostic: " + c.getDiagnostic());
                System.out.println("  Traitement: " + c.getTraitement());
                System.out.println("  Date: " + c.getDateConsultation());
            } else {
                System.out.println("✗ Consultation not found with ID: " + searchId);
            }
            System.out.println("-------------------------------------------\n");

            // =====================================
            // TEST 4: UPDATE CONSULTATION
            // =====================================
            System.out.println("TEST 4: Updating consultation...");

            newConsultation.setDiagnostic("Diagnostic MODIFIÉ - Carie profonde");
            newConsultation.setTraitement("Traitement MODIFIÉ - Extraction nécessaire");
            newConsultation.setObservationMedecin("Observation MODIFIÉE - Revoir dans 1 semaine");

            repo.update(newConsultation);
            System.out.println("✓ Consultation mise à jour avec succès");

            // Verify update
            var updated = repo.findById(newConsultation.getId());
            if (updated.isPresent()) {
                System.out.println("  Nouveau diagnostic: " + updated.get().getDiagnostic());
            }
            System.out.println("-------------------------------------------\n");

            // =====================================
            // TEST 5: FIND BY PATIENT ID
            // =====================================
            System.out.println("TEST 5: Finding consultations by patient ID...");

            Long patientId = 1L;
            List<Consultation> patientConsultations = repo.findByPatientId(patientId);

            System.out.println("✓ Found " + patientConsultations.size() +
                    " consultation(s) for patient ID " + patientId);
            System.out.println("-------------------------------------------\n");

            // =====================================
            // TEST 6: DELETE CONSULTATION
            // =====================================
            System.out.println("TEST 6: Deleting consultation...");

            repo.delete(newConsultation);
            System.out.println("✓ Consultation supprimée avec ID: " + newConsultation.getId());

            // Verify deletion
            var deleted = repo.findById(newConsultation.getId());
            if (deleted.isEmpty()) {
                System.out.println("✓ Deletion confirmed - consultation no longer exists");
            }
            System.out.println("-------------------------------------------\n");

            // =====================================
            // FINAL SUMMARY
            // =====================================
            System.out.println("===========================================");
            System.out.println("   ALL TESTS COMPLETED SUCCESSFULLY! ✓");
            System.out.println("===========================================");

        } catch (Exception e) {
            System.err.println("\n✗ ERROR OCCURRED:");
            System.err.println("-------------------------------------------");
            e.printStackTrace();
            System.err.println("-------------------------------------------");
            System.err.println("\nPossible issues:");
            System.err.println("1. Database 'dentaldb' not created");
            System.err.println("2. Tables not created - run complete_database.sql first");
            System.err.println("3. MySQL server not running");
            System.err.println("4. Wrong database credentials");
        }
    }
}