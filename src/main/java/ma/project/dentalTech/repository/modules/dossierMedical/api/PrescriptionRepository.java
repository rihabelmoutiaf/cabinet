package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Prescription;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository {

    List<Prescription> findAll();

    Optional<Prescription> findById(Long id);

    void create(Prescription prescription);

    void update(Prescription prescription);

    void delete(Prescription prescription);

    void deleteById(Long id);

    List<Prescription> findByPatientId(Long patientId);

    List<Prescription> findByUtilisateurId(Long utilisateurId);

    List<Prescription> findByDatePrescriptionBetween(LocalDateTime debut, LocalDateTime fin);
}
