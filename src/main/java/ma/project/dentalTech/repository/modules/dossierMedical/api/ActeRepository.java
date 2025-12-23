package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Acte;
import java.util.List;

public interface ActeRepository {

    Acte save(Acte acte);

    Acte findById(Long id);

    Acte findByNom(String nom);

    List<Acte> findAll();

    void update(Acte acte);

    void delete(Long id);
    List<Acte> findByConsultationId(Long consultationId);
    List<Acte> findByTypeActe(String typeActe);
    double sumTarifByConsultation(Long consultationId);
}
