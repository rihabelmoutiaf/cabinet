package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Medecin;

import java.util.List;

public interface MedecinRepository {

    Medecin save(Medecin medecin);

    Medecin findById(Long id);

    Medecin findByEmail(String email);

    List<Medecin> findBySpecialite(String specialite);

    List<Medecin> findAll();

    void update(Medecin medecin);

    void delete(Long id);
}
