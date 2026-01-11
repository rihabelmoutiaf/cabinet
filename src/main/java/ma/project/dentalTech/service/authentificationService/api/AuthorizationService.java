package ma.project.dentalTech.service.authentificationService.api;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;

public interface AuthorizationService {

    boolean hasRole(UserPrincipal principal, RoleType role);

    boolean hasAnyRole(UserPrincipal principal, RoleType... roles);

    boolean hasPrivilege(UserPrincipal principal, String privilege);

    void checkRole(UserPrincipal principal, RoleType role);

    void checkAnyRole(UserPrincipal principal, RoleType... roles);

    void checkPrivilege(UserPrincipal principal, String privilege);
}
