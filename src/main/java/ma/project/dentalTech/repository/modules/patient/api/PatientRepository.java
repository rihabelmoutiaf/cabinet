package ma.project.dentalTech.repository.modules.patient.api;

import ma.project.dentalTech.entities.patient.Patient;

import java.util.List;

public interface PatientRepository {

    Patient save(Patient patient);

    Patient findById(Long id);

    List<Patient> findAll();

    List<Patient> findByNom(String nom);

    void update(Patient patient);

    void delete(Long id);
}
