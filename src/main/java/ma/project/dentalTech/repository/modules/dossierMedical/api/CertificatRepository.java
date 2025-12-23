package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Certificat;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CertificatRepository extends CrudRepository<Certificat, Long> {

    List<Certificat> findByPatientId(Long patientId);

    List<Certificat> findByUtilisateurId(Long utilisateurId);

    List<Certificat> findByDateEmissionBetween(LocalDateTime debut, LocalDateTime fin);
}
