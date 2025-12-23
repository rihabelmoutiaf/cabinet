package ma.project.dentalTech.service.modules.auth.api;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Utilisateur;

/**
 * Service d'autorisation pour gérer les permissions et rôles
 */
public interface AuthorizationService {

    /**
     * Vérifie si l'utilisateur a un rôle spécifique
     *
     * @param user L'utilisateur à vérifier
     * @param roleType Le type de rôle requis
     * @return true si l'utilisateur a le rôle
     */
    boolean hasRole(Utilisateur user, RoleType roleType);

    /**
     * Vérifie si l'utilisateur a l'un des rôles spécifiés
     *
     * @param user L'utilisateur à vérifier
     * @param roleTypes Les types de rôles acceptés
     * @return true si l'utilisateur a au moins un des rôles
     */
    boolean hasAnyRole(Utilisateur user, RoleType... roleTypes);

    /**
     * Vérifie si l'utilisateur est administrateur
     *
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est admin
     */
    boolean isAdmin(Utilisateur user);

    /**
     * Vérifie si l'utilisateur est médecin
     *
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est médecin
     */
    boolean isMedecin(Utilisateur user);

    /**
     * Vérifie si l'utilisateur est secrétaire
     *
     * @param user L'utilisateur à vérifier
     * @return true si l'utilisateur est secrétaire
     */
    boolean isSecretaire(Utilisateur user);

    /**
     * Vérifie si l'utilisateur peut accéder à une ressource
     *
     * @param user L'utilisateur
     * @param resourceType Type de ressource
     * @param action Action à effectuer (READ, WRITE, DELETE, etc.)
     * @return true si l'accès est autorisé
     */
    boolean canAccess(Utilisateur user, String resourceType, String action);

    /**
     * Vérifie si l'utilisateur peut gérer d'autres utilisateurs
     *
     * @param user L'utilisateur
     * @return true si autorisé
     */
    boolean canManageUsers(Utilisateur user);

    /**
     * Vérifie si l'utilisateur peut voir les données financières
     *
     * @param user L'utilisateur
     * @return true si autorisé
     */
    boolean canViewFinancialData(Utilisateur user);

    /**
     * Vérifie si l'utilisateur peut modifier les données d'un patient
     *
     * @param user L'utilisateur
     * @return true si autorisé
     */
    boolean canEditPatientData(Utilisateur user);
}