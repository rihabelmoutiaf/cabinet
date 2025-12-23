package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Acte;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface ActeRepository extends CrudRepository<Acte, Long> {

    List<Acte> findByConsultationId(Long consultationId);

    List<Acte> findByTypeActe(String typeActe);

    double sumTarifByConsultation(Long consultationId);
}

