package ma.project.dentalTech.service.modules.auth.impl;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;
import ma.project.dentalTech.service.modules.auth.api.AuthService;
import ma.project.dentalTech.service.modules.auth.api.PasswordEncoder;

import java.time.LocalDate;

/**
 * Implémentation du service d'authentification
 */
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // Session utilisateur (en mémoire pour l'instant)
    private Utilisateur currentUser;

    public AuthServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = new PasswordEncoder();
        this.currentUser = null;
    }

    @Override
    public Utilisateur authenticate(String login, String password) {
        if (login == null || login.trim().isEmpty()) {
            System.out.println("❌ Login vide");
            return null;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("❌ Mot de passe vide");
            return null;
        }

        // Rechercher l'utilisateur par email ou login
        Utilisateur user = utilisateurRepository.findByEmail(login);

        if (user == null) {
            System.out.println("❌ Utilisateur non trouvé: " + login);
            return null;
        }

        // Vérifier si le compte est actif
        if (!user.isActive()) {
            System.out.println("❌ Compte désactivé: " + login);
            return null;
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(password, user.getMotDePasse())) {
            System.out.println("❌ Mot de passe incorrect pour: " + login);
            return null;
        }

        // Mettre à jour la date de dernière connexion
        user.setDateDerniereConnexion(LocalDate.now());
        utilisateurRepository.update(user);

        // Stocker l'utilisateur en session
        this.currentUser = user;

        System.out.println("✅ Authentification réussie: " + user.getNom() + " " + user.getPrenom());
        return user;
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.println("👋 Déconnexion de: " + currentUser.getNom());
            this.currentUser = null;
        }
    }

    @Override
    public Utilisateur getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public boolean isAuthenticated() {
        return this.currentUser != null;
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        if (!isAuthenticated()) {
            System.out.println("❌ Aucun utilisateur connecté");
            return false;
        }

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(oldPassword, currentUser.getMotDePasse())) {
            System.out.println("❌ Ancien mot de passe incorrect");
            return false;
        }

        // Valider le nouveau mot de passe
        if (!validatePasswordStrength(newPassword)) {
            System.out.println("❌ Le nouveau mot de passe ne respecte pas les critères de sécurité");
            return false;
        }

        // Encoder et sauvegarder le nouveau mot de passe
        String hashedPassword = passwordEncoder.encode(newPassword);
        currentUser.setMotDePasse(hashedPassword);

        utilisateurRepository.update(currentUser);

        System.out.println("✅ Mot de passe changé avec succès");
        return true;
    }

    @Override
    public boolean resetPassword(Long userId, String newPassword) {
        if (!isAuthenticated()) {
            System.out.println("❌ Aucun utilisateur connecté");
            return false;
        }

        // Vérifier que l'utilisateur actuel est admin
        // TODO: Vérifier le rôle via AuthorizationService

        // Valider le nouveau mot de passe
        if (!validatePasswordStrength(newPassword)) {
            System.out.println("❌ Le nouveau mot de passe ne respecte pas les critères de sécurité");
            return false;
        }

        // Récupérer l'utilisateur cible
        Utilisateur targetUser = utilisateurRepository.findById(userId);
        if (targetUser == null) {
            System.out.println("❌ Utilisateur non trouvé: " + userId);
            return false;
        }

        // Encoder et sauvegarder le nouveau mot de passe
        String hashedPassword = passwordEncoder.encode(newPassword);
        targetUser.setMotDePasse(hashedPassword);

        utilisateurRepository.update(targetUser);

        System.out.println("✅ Mot de passe réinitialisé pour: " + targetUser.getNom());
        return true;
    }

    @Override
    public boolean validatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        // Au moins une majuscule
        boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);

        // Au moins une minuscule
        boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);

        // Au moins un chiffre
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        // Au moins un caractère spécial
        boolean hasSpecialChar = password.chars().anyMatch(c ->
                "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0
        );

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}