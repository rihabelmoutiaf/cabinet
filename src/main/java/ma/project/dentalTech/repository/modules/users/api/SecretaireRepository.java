package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Secretaire;

import java.util.List;

public interface SecretaireRepository {

    Secretaire save(Secretaire secretaire);

    Secretaire findById(Long id);

    Secretaire findByEmail(String email);

    List<Secretaire> findAll();

    void update(Secretaire secretaire);

    void delete(Long id);
}
