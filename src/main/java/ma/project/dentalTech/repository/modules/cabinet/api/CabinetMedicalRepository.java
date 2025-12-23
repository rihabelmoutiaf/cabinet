package ma.project.dentalTech.repository.modules.cabinet.api;

import ma.project.dentalTech.entities.cabinet.CabinetMedical;

import java.util.List;

public interface CabinetMedicalRepository {

    CabinetMedical save(CabinetMedical cabinet);

    CabinetMedical findById(Long id);

    List<CabinetMedical> findAll();

    CabinetMedical findByNom(String nom);

    void update(CabinetMedical cabinet);

    void delete(Long id);
}
