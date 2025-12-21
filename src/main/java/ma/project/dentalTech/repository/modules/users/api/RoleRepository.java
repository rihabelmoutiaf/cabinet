package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.repository.common.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleType(RoleType roleType);
}
