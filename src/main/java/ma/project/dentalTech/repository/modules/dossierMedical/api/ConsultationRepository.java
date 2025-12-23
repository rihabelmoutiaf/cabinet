package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    List<Consultation> findByPatientId(Long patientId);

    List<Consultation> findByUtilisateurId(Long utilisateurId);

    List<Consultation> findByMedecinName(String nomMedecin);

    List<Consultation> findByDateConsultationBetween(LocalDateTime debut, LocalDateTime fin);
}
