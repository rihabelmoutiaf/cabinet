package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.SituationFinanciere;

import java.util.List;
import java.util.Optional;

public interface SituationFinanciereRepository {

    List<SituationFinanciere> findAll();

    Optional<SituationFinanciere> findById(Long id);

    void create(SituationFinanciere situation);

    void update(SituationFinanciere situation);

    void delete(SituationFinanciere situation);

    void deleteById(Long id);

    List<SituationFinanciere> findByPatientId(Long patientId);
}
