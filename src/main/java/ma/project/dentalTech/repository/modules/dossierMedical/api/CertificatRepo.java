package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Certificat;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CertificatRepo extends CrudRepository<Certificat, Long> {

    List<Certificat> findByPatientId(Long patientId);

    List<Certificat> findByConsultationId(Long consultationId);

    List<Certificat> findByDateDebutBetween(LocalDate debut, LocalDate fin);
}
