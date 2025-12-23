package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.entities.enums.StatutConsultation;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    List<Consultation> findByDossierMedicalId(Long dossierMedicalId);

    List<Consultation> findByMedecinId(Long medecinId);

    List<Consultation> findByStatut(StatutConsultation statut);

    List<Consultation> findByDateConsultationBetween(LocalDate debut, LocalDate fin);

    Optional<Consultation> findLastByDossierMedical(Long dossierMedicalId);
}
