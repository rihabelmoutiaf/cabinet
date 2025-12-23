package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.InterventionMedecin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InterventionMedecinRepository {

    List<InterventionMedecin> findAll();

    Optional<InterventionMedecin> findById(Long id);

    void create(InterventionMedecin intervention);

    void update(InterventionMedecin intervention);

    void delete(InterventionMedecin intervention);

    void deleteById(Long id);

    List<InterventionMedecin> findByPatientId(Long patientId);

    List<InterventionMedecin> findByUtilisateurId(Long utilisateurId);

    List<InterventionMedecin> findByDateInterventionBetween(LocalDateTime debut, LocalDateTime fin);
}
