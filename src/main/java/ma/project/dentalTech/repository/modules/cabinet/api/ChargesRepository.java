package ma.project.dentalTech.repository.modules.cabinet.api;

import ma.project.dentalTech.entities.cabinet.Charges;

import java.util.List;

public interface ChargesRepository {

    Charges save(Charges charge);

    Charges findById(Long id);

    List<Charges> findAll();

    List<Charges> findByUtilisateur(Long utilisateurId);

    void update(Charges charge);

    void delete(Long id);
}
