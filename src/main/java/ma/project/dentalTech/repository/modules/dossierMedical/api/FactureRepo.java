package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Facture;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface FactureRepo extends CrudRepository<Facture, Long> {

    List<Facture> findByConsultationId(Long consultationId);

    List<Facture> findByStatut(String statut);

    double sumMontantByDossierMedical(Long dossierMedicalId);
}
