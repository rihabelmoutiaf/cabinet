package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Ordonnance;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrdonnanceRepo extends CrudRepository<Ordonnance, Long> {

    List<Ordonnance> findByConsultationId(Long consultationId);

    Optional<Ordonnance> findByConsultation(Long consultationId);
}
