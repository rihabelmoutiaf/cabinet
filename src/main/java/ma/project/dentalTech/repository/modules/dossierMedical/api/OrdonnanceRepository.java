package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Ordonnance;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrdonnanceRepository extends CrudRepository<Ordonnance, Long> {

    List<Ordonnance> findByPatientId(Long patientId);

    List<Ordonnance> findByUtilisateurId(Long utilisateurId);

    List<Ordonnance> findByDateOrdonnanceBetween(LocalDateTime debut, LocalDateTime fin);
}
