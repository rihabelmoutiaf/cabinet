package ma.project.dentalTech.service.modules.auth.impl;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.modules.users.api.RoleRepository;
import ma.project.dentalTech.service.modules.auth.api.AuthorizationService;

import java.util.Arrays;

/**
 * Implémentation du service d'autorisation
 */
public class AuthorizationServiceImpl implements AuthorizationService {

    private final RoleRepository roleRepository;

    public AuthorizationServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean hasRole(Utilisateur user, RoleType roleType) {
        if (user == null || roleType == null) {
            return false;
        }

        // Récupérer le rôle de l'utilisateur
        Role userRole = getUserRole(user);
        if (userRole == null) {
            return false;
        }

        return userRole.getNom().equals(roleType.name());
    }

    @Override
    public boolean hasAnyRole(Utilisateur user, RoleType... roleTypes) {
        if (user == null || roleTypes == null || roleTypes.length == 0) {
            return false;
        }

        return Arrays.stream(roleTypes)
                .anyMatch(roleType -> hasRole(user, roleType));
    }

    @Override
    public boolean isAdmin(Utilisateur user) {
        return hasRole(user, RoleType.ADMIN);
    }

    @Override
    public boolean isMedecin(Utilisateur user) {
        return hasRole(user, RoleType.MEDECIN);
    }

    @Override
    public boolean isSecretaire(Utilisateur user) {
        return hasRole(user, RoleType.SECRETAIRE);
    }

    @Override
    public boolean canAccess(Utilisateur user, String resourceType, String action) {
        if (user == null) {
            return false;
        }

        // Les admins ont tous les droits
        if (isAdmin(user)) {
            return true;
        }

        // Règles spécifiques par ressource
        switch (resourceType.toUpperCase()) {
            case "PATIENT":
                return canAccessPatientResource(user, action);
            case "RDV":
                return canAccessRdvResource(user, action);
            case "CONSULTATION":
                return canAccessConsultationResource(user, action);
            case "FACTURE":
                return canAccessFactureResource(user, action);
            case "USER":
                return canAccessUserResource(user, action);
            default:
                return false;
        }
    }

    @Override
    public boolean canManageUsers(Utilisateur user) {
        // Seul l'admin peut gérer les utilisateurs
        return isAdmin(user);
    }

    @Override
    public boolean canViewFinancialData(Utilisateur user) {
        // Admin et Médecin peuvent voir les données financières
        return isAdmin(user) || isMedecin(user);
    }

    @Override
    public boolean canEditPatientData(Utilisateur user) {
        // Admin, Médecin et Secrétaire peuvent modifier les données patient
        return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
    }

    // ========== MÉTHODES PRIVÉES ==========

    /**
     * Récupère le rôle d'un utilisateur
     */
    private Role getUserRole(Utilisateur user) {
        if (user.getRole() != null) {
            return user.getRole();
        }

        if (user.getRoleId() != null) {
            return roleRepository.findById(user.getRoleId());
        }

        return null;
    }

    /**
     * Vérifie l'accès aux ressources Patient
     */
    private boolean canAccessPatientResource(Utilisateur user, String action) {
        switch (action.toUpperCase()) {
            case "READ":
                // Tous peuvent lire
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
            case "CREATE":
            case "UPDATE":
                // Tous peuvent créer/modifier
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
            case "DELETE":
                // Seul l'admin peut supprimer
                return isAdmin(user);
            default:
                return false;
        }
    }

    /**
     * Vérifie l'accès aux ressources RDV
     */
    private boolean canAccessRdvResource(Utilisateur user, String action) {
        switch (action.toUpperCase()) {
            case "READ":
                // Tous peuvent lire
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
            case "CREATE":
            case "UPDATE":
                // Médecin et Secrétaire peuvent gérer les RDV
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
            case "DELETE":
                // Admin et Médecin peuvent supprimer
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN);
            default:
                return false;
        }
    }

    /**
     * Vérifie l'accès aux ressources Consultation
     */
    private boolean canAccessConsultationResource(Utilisateur user, String action) {
        switch (action.toUpperCase()) {
            case "READ":
                // Admin et Médecin peuvent lire
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN);
            case "CREATE":
            case "UPDATE":
                // Seul le médecin peut créer/modifier
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN);
            case "DELETE":
                // Seul l'admin peut supprimer
                return isAdmin(user);
            default:
                return false;
        }
    }

    /**
     * Vérifie l'accès aux ressources Facture
     */
    private boolean canAccessFactureResource(Utilisateur user, String action) {
        switch (action.toUpperCase()) {
            case "READ":
                // Admin, Médecin et Secrétaire peuvent lire
                return hasAnyRole(user, RoleType.ADMIN, RoleType.MEDECIN, RoleType.SECRETAIRE);
            case "CREATE":
            case "UPDATE":
                // Admin et Secrétaire peuvent gérer les factures
                return hasAnyRole(user, RoleType.ADMIN, RoleType.SECRETAIRE);
            case "DELETE":
                // Seul l'admin peut supprimer
                return isAdmin(user);
            default:
                return false;
        }
    }

    /**
     * Vérifie l'accès aux ressources User
     */
    private boolean canAccessUserResource(Utilisateur user, String action) {
        // Seul l'admin peut gérer les utilisateurs
        return isAdmin(user);
    }
}