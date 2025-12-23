package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.InterventionMedecin;
import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface InterventionMedecinRepo extends CrudRepository<InterventionMedecin, Long> {

    List<InterventionMedecin> findByConsultationId(Long consultationId);

    List<InterventionMedecin> findByRole(RoleType role);
}
