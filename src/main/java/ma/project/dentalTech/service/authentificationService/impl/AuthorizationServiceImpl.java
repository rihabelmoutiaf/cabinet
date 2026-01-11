package ma.project.dentalTech.service.authentificationService.impl;

import java.util.Set;
import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.security.Privileges;
import ma.project.dentalTech.service.authentificationService.api.AuthorizationService;

public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public boolean hasRole(UserPrincipal principal, RoleType role) {
        if (principal == null || role == null || principal.roles() == null) {
            return false;
        }
        return principal.roles().contains(role);
    }

    @Override
    public boolean hasAnyRole(UserPrincipal principal, RoleType... roles) {
        if (principal == null || roles == null || roles.length == 0) {
            return false;
        }
        for (RoleType role : roles) {
            if (hasRole(principal, role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasPrivilege(UserPrincipal principal, String privilege) {
        if (principal == null || privilege == null || privilege.isBlank()) {
            return false;
        }
        Set<String> privileges = principal.privileges();
        if (privileges != null && privileges.contains(privilege)) {
            return true;
        }
        if (principal.roles() != null) {
            for (RoleType role : principal.roles()) {
                if (Privileges.forRole(role).contains(privilege)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void checkRole(UserPrincipal principal, RoleType role) {
        if (!hasRole(principal, role)) {
            throw new IllegalStateException("Role not granted: " + role);
        }
    }

    @Override
    public void checkAnyRole(UserPrincipal principal, RoleType... roles) {
        if (!hasAnyRole(principal, roles)) {
            throw new IllegalStateException("No role granted");
        }
    }

    @Override
    public void checkPrivilege(UserPrincipal principal, String privilege) {
        if (!hasPrivilege(principal, privilege)) {
            throw new IllegalStateException("Privilege not granted: " + privilege);
        }
    }
}
