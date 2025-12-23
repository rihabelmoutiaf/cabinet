package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Medicament;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MedicamentRepo extends CrudRepository<Medicament, Long> {

    Optional<Medicament> findByNom(String nom);

    List<Medicament> searchByNom(String keyword);
}

