package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.SituationFinanciere;
import ma.project.dentalTech.entities.enums.StatutSituationFinanciere;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SituationFinanciereRepo extends CrudRepository<SituationFinanciere, Long> {

    Optional<SituationFinanciere> findByDossierMedicalId(Long dossierMedicalId);

    List<SituationFinanciere> findByStatut(StatutSituationFinanciere statut);

    double getSoldeByDossierMedical(Long dossierMedicalId);
}
