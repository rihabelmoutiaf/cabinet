package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    /**
     * Trouve un admin par son email
     */
    Admin findByEmail(String email);

    /**
     * Trouve tous les admins actifs
     */
    List<Admin> findAllActive();

    /**
     * Compte le nombre total d'admins
     */
    long countAdmins();
}
