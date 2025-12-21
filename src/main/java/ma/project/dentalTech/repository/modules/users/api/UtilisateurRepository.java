package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.common.CrudRepository;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);
}
