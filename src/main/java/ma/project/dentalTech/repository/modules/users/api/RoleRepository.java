package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Role;

import java.util.List;

public interface RoleRepository {

    Role save(Role role);

    Role findById(Long id);

    Role findByNom(String nom);

    List<Role> findAll();

    void update(Role role);

    void delete(Long id);
}
