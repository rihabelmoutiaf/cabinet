package ma.project.dentalTech;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.modules.users.api.RoleRepository;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;
import ma.project.dentalTech.repository.modules.users.impl.RoleRepositoryImpl;
import ma.project.dentalTech.repository.modules.users.impl.UtilisateurRepositoryImpl;
import ma.project.dentalTech.service.modules.auth.api.AuthService;
import ma.project.dentalTech.service.modules.auth.api.AuthorizationService;
import ma.project.dentalTech.service.modules.auth.impl.AuthServiceImpl;
import ma.project.dentalTech.service.modules.auth.impl.AuthorizationServiceImpl;

/**
 * Classe principale pour tester le système d'authentification
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  DentalTech - Système d'Authentification");
        System.out.println("==============================================\n");

        // Initialisation des repositories
        UtilisateurRepository userRepo = new UtilisateurRepositoryImpl();
        RoleRepository roleRepo = new RoleRepositoryImpl();

        // Initialisation des services
        AuthService authService = new AuthServiceImpl(userRepo);
        AuthorizationService authzService = new AuthorizationServiceImpl(roleRepo);

        // ========== TEST 1: Connexion avec des credentials invalides ==========
        System.out.println("TEST 1: Tentative de connexion avec login invalide");
        System.out.println("---------------------------------------------------");
        Utilisateur user = authService.authenticate("invalid@email.com", "wrongpass");
        if (user == null) {
            System.out.println("✅ Connexion refusée comme prévu\n");
        }

        // ========== TEST 2: Connexion avec le compte admin par défaut ==========
        System.out.println("TEST 2: Connexion avec le compte admin");
        System.out.println("---------------------------------------");
        System.out.println("Login: admin");
        System.out.println("Password: Admin@123");

        user = authService.authenticate("admin", "Admin@123");

        if (user != null) {
            System.out.println("✅ Connexion réussie!");
            System.out.println("   Utilisateur: " + user.getNom() + " " + user.getPrenom());
            System.out.println("   Email: " + user.getEmail());
            System.out.println("   Actif: " + user.isActive());
        } else {
            System.out.println("❌ Échec de connexion");
            return;
        }

        // ========== TEST 3: Vérification du statut d'authentification ==========
        System.out.println("\nTEST 3: Vérification du statut");
        System.out.println("--------------------------------");
        if (authService.isAuthenticated()) {
            System.out.println("✅ Utilisateur authentifié");
            Utilisateur currentUser = authService.getCurrentUser();
            System.out.println("   Utilisateur actuel: " + currentUser.getNom());
        }

        // ========== TEST 4: Vérification des autorisations ==========
        System.out.println("\nTEST 4: Vérification des autorisations");
        System.out.println("---------------------------------------");

        boolean isAdmin = authzService.isAdmin(user);
        System.out.println("Est Admin? " + (isAdmin ? "✅ Oui" : "❌ Non"));

        boolean canManageUsers = authzService.canManageUsers(user);
        System.out.println("Peut gérer les utilisateurs? " + (canManageUsers ? "✅ Oui" : "❌ Non"));

        boolean canViewFinancial = authzService.canViewFinancialData(user);
        System.out.println("Peut voir les données financières? " + (canViewFinancial ? "✅ Oui" : "❌ Non"));

        boolean canEditPatient = authzService.canEditPatientData(user);
        System.out.println("Peut modifier les données patient? " + (canEditPatient ? "✅ Oui" : "❌ Non"));

        // ========== TEST 5: Vérification des accès aux ressources ==========
        System.out.println("\nTEST 5: Vérification des accès aux ressources");
        System.out.println("----------------------------------------------");

        testResourceAccess(authzService, user, "PATIENT", "READ");
        testResourceAccess(authzService, user, "PATIENT", "CREATE");
        testResourceAccess(authzService, user, "PATIENT", "DELETE");
        testResourceAccess(authzService, user, "RDV", "UPDATE");
        testResourceAccess(authzService, user, "CONSULTATION", "CREATE");
        testResourceAccess(authzService, user, "FACTURE", "READ");
        testResourceAccess(authzService, user, "USER", "CREATE");

        // ========== TEST 6: Validation de mot de passe ==========
        System.out.println("\nTEST 6: Validation de la force du mot de passe");
        System.out.println("----------------------------------------------");
        testPasswordStrength(authService, "weak");
        testPasswordStrength(authService, "StrongPass1!");
        testPasswordStrength(authService, "Admin@123");

        // ========== TEST 7: Déconnexion ==========
        System.out.println("\nTEST 7: Déconnexion");
        System.out.println("-------------------");
        authService.logout();
        System.out.println("Authentifié? " + (authService.isAuthenticated() ? "✅ Oui" : "❌ Non"));

        System.out.println("\n==============================================");
        System.out.println("  Tests terminés avec succès!");
        System.out.println("==============================================");
    }

    /**
     * Teste l'accès à une ressource
     */
    private static void testResourceAccess(AuthorizationService authzService,
                                           Utilisateur user,
                                           String resource,
                                           String action) {
        boolean canAccess = authzService.canAccess(user, resource, action);
        String status = canAccess ? "✅ Autorisé" : "❌ Refusé";
        System.out.printf("  %s: %s %s\n", status, action, resource);
    }

    /**
     * Teste la force d'un mot de passe
     */
    private static void testPasswordStrength(AuthService authService, String password) {
        boolean isValid = authService.validatePasswordStrength(password);
        String status = isValid ? "✅ Valide" : "❌ Invalide";
        System.out.printf("  %s: \"%s\"\n", status, password);
    }
}