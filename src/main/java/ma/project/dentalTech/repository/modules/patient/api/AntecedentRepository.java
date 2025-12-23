package ma.project.dentalTech.repository.modules.patient.api;

import ma.project.dentalTech.entities.patient.Antecedent;

import java.util.List;

public interface AntecedentRepository {

    Antecedent save(Antecedent antecedent);

    Antecedent findById(Long id);

    List<Antecedent> findByPatient(Long patientId);

    List<Antecedent> findByCategorie(String categorie);

    List<Antecedent> findAll();

    void update(Antecedent antecedent);

    void delete(Long id);
}
