package ma.project.dentalTech.service.modules.users.api;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.entities.users.Secretaire;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.service.modules.users.dto.*;

import java.util.List;

/**
 * Service de gestion des utilisateurs
 */
public interface UserManagementService {

    // ========== CRÉATION D'UTILISATEURS ==========

    /**
     * Crée un nouvel administrateur
     */
    Admin createAdmin(CreateAdminRequest request);

    /**
     * Crée un nouveau médecin
     */
    Medecin createMedecin(CreateMedecinRequest request);

    /**
     * Crée une nouvelle secrétaire
     */
    Secretaire createSecretaire(CreateSecretaireRequest request);

    // ========== RÉCUPÉRATION D'UTILISATEURS ==========

    /**
     * Récupère un utilisateur par son ID
     */
    Utilisateur getUserById(Long id);

    /**
     * Récupère un utilisateur par son email
     */
    Utilisateur getUserByEmail(String email);

    /**
     * Récupère un utilisateur par son login
     */
    Utilisateur getUserByLogin(String login);

    /**
     * Récupère tous les utilisateurs
     */
    List<Utilisateur> getAllUsers();

    /**
     * Récupère les utilisateurs par rôle
     */
    List<Utilisateur> getUsersByRole(RoleType roleType);

    /**
     * Récupère tous les médecins
     */
    List<Medecin> getAllMedecins();

    /**
     * Récupère toutes les secrétaires
     */
    List<Secretaire> getAllSecretaires();

    /**
     * Récupère tous les admins
     */
    List<Admin> getAllAdmins();

    // ========== MISE À JOUR D'UTILISATEURS ==========

    /**
     * Met à jour le profil d'un utilisateur
     */
    Utilisateur updateUserProfile(Long userId, UpdateUserProfileRequest request);

    /**
     * Active un utilisateur
     */
    boolean activateUser(Long userId);

    /**
     * Désactive un utilisateur
     */
    boolean deactivateUser(Long userId);

    /**
     * Change le rôle d'un utilisateur
     */
    boolean changeUserRole(Long userId, Long newRoleId);

    // ========== SUPPRESSION D'UTILISATEURS ==========

    /**
     * Supprime un utilisateur
     */
    boolean deleteUser(Long userId);

    /**
     * Suppression logique (désactivation)
     */
    boolean softDeleteUser(Long userId);

    // ========== STATISTIQUES ET VALIDATION ==========

    /**
     * Compte le nombre total d'utilisateurs actifs
     */
    long countActiveUsers();

    /**
     * Compte les utilisateurs par rôle
     */
    long countUsersByRole(RoleType roleType);

    /**
     * Vérifie si un email existe déjà
     */
    boolean emailExists(String email);

    /**
     * Vérifie si un login existe déjà
     */
    boolean loginExists(String login);

    /**
     * Récupère les informations de compte d'un utilisateur
     */
    UserAccountDto getUserAccountInfo(Long userId);

    /**
     * Récupère tous les utilisateurs actifs
     */
    List<Utilisateur> getAllActiveUsers();
}