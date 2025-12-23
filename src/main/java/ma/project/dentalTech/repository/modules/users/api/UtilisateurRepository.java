package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);

    Utilisateur findByLogin(String login);

    List<Utilisateur> findByTypeUser(String typeUser);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    void updateLastLogin(Long userId);

    void incrementFailedAttempts(Long userId);

    void resetFailedAttempts(Long userId);

    void lockAccount(Long userId);

    void unlockAccount(Long userId);
}
