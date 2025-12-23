package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.DossierMedical;

import java.util.List;
import java.util.Optional;

public interface DossierMedicalRepository {

    List<DossierMedical> findAll();

    Optional<DossierMedical> findById(Long id);

    void create(DossierMedical dossierMedical);

    void update(DossierMedical dossierMedical);

    void delete(DossierMedical dossierMedical);

    void deleteById(Long id);

    Optional<DossierMedical> findByPatientId(Long patientId);
}
