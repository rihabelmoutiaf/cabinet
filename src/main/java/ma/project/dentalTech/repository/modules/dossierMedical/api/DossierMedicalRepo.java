package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.DossierMedical;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DossierMedicalRepo extends CrudRepository<DossierMedical, Long> {

    Optional<DossierMedical> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);

    List<DossierMedical> findByMedecinId(Long medecinId);
}
