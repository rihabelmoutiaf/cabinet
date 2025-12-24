package ma.project.dentalTech.service.modules.users.api;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Role;

import java.util.List;

/**
 * Service de gestion des rôles
 */
public interface RoleManagementService {

    /**
     * Crée un nouveau rôle
     */
    Role createRole(String nom, String description);

    /**
     * Récupère un rôle par son ID
     */
    Role getRoleById(Long id);

    /**
     * Récupère un rôle par son nom
     */
    Role getRoleByName(String nom);

    /**
     * Récupère un rôle par son type
     */
    Role getRoleByType(RoleType roleType);

    /**
     * Récupère tous les rôles
     */
    List<Role> getAllRoles();

    /**
     * Met à jour un rôle
     */
    Role updateRole(Long id, String nom, String description);

    /**
     * Supprime un rôle
     */
    boolean deleteRole(Long id);

    /**
     * Vérifie si un rôle existe par son nom
     */
    boolean roleExists(String nom);

    /**
     * Compte le nombre d'utilisateurs ayant un rôle spécifique
     */
    long countUsersWithRole(Long roleId);

    /**
     * Initialise les rôles par défaut du système
     */
    void initializeDefaultRoles();
}