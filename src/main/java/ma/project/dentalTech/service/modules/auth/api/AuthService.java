package ma.project.dentalTech.service.modules.auth.api;

import ma.project.dentalTech.entities.users.Utilisateur;

/**
 * Service d'authentification pour gérer la connexion et la session utilisateur
 */
public interface AuthService {

    /**
     * Authentifie un utilisateur avec login et mot de passe
     *
     * @param login Login ou email de l'utilisateur
     * @param password Mot de passe en clair
     * @return L'utilisateur authentifié ou null si échec
     */
    Utilisateur authenticate(String login, String password);

    /**
     * Déconnecte l'utilisateur actuel
     */
    void logout();

    /**
     * Récupère l'utilisateur actuellement connecté
     *
     * @return L'utilisateur connecté ou null si aucun
     */
    Utilisateur getCurrentUser();

    /**
     * Vérifie si un utilisateur est actuellement connecté
     *
     * @return true si un utilisateur est connecté
     */
    boolean isAuthenticated();

    /**
     * Change le mot de passe de l'utilisateur connecté
     *
     * @param oldPassword Ancien mot de passe
     * @param newPassword Nouveau mot de passe
     * @return true si le changement a réussi
     */
    boolean changePassword(String oldPassword, String newPassword);

    /**
     * Réinitialise le mot de passe d'un utilisateur (admin uniquement)
     *
     * @param userId ID de l'utilisateur
     * @param newPassword Nouveau mot de passe
     * @return true si la réinitialisation a réussi
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * Valide la force d'un mot de passe
     *
     * @param password Mot de passe à valider
     * @return true si le mot de passe est valide
     */
    boolean validatePasswordStrength(String password);
}