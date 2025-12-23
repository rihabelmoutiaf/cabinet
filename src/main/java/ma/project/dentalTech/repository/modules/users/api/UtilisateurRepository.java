package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Utilisateur;

import java.util.List;

public interface UtilisateurRepository<T extends Utilisateur> {

    T save(T utilisateur);

    T findById(Long id);

    T findByEmail(String email);

    List<T> findAll();

    List<T> findByRoleId(Long roleId);

    void update(T utilisateur);

    void delete(Long id);
}
