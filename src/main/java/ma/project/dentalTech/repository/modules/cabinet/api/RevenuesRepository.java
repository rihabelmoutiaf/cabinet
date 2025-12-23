package ma.project.dentalTech.repository.modules.cabinet.api;

import ma.project.dentalTech.entities.cabinet.Revenues;

import java.util.List;

public interface RevenuesRepository {

    Revenues save(Revenues revenue);

    Revenues findById(Long id);

    List<Revenues> findAll();

    List<Revenues> findByUtilisateur(Long utilisateurId);

    void update(Revenues revenue);

    void delete(Long id);
}
