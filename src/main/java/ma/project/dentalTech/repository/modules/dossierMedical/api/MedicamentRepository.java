package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Medicament;

import java.util.List;
import java.util.Optional;

public interface MedicamentRepository {

    List<Medicament> findAll();

    Optional<Medicament> findById(Long id);

    void create(Medicament medicament);

    void update(Medicament medicament);

    void delete(Medicament medicament);

    void deleteById(Long id);

    List<Medicament> findByNom(String nom);

    List<Medicament> findByLaboratoire(String laboratoire);
}
