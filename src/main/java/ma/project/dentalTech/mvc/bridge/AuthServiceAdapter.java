package ma.project.dentalTech.mvc.bridge;

import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.service.modules.auth.api.AuthService;
import ma.project.dentalTech.service.modules.auth.api.AuthorizationService;

public class AuthServiceAdapter {

    private final AuthService authService;
    private final AuthorizationService authorizationService;

    public AuthServiceAdapter(AuthService authService, AuthorizationService authorizationService) {
        this.authService = authService;
        this.authorizationService = authorizationService;
    }

    public UserView login(String login, String password) {
        Utilisateur user = authService.authenticate(login, password);
        if (user == null) {
            return null;
        }
        return UserView.from(user, authorizationService);
    }

    public void logout() {
        authService.logout();
    }

    public boolean isAuthenticated() {
        return authService.isAuthenticated();
    }

    public Utilisateur currentEntity() {
        return authService.getCurrentUser();
    }

    public UserView currentUser() {
        Utilisateur user = authService.getCurrentUser();
        return user == null ? null : UserView.from(user, authorizationService);
    }

    public boolean hasRole(Utilisateur user, RoleType roleType) {
        return authorizationService.hasRole(user, roleType);
    }

    public static final class UserView {
        private final Long id;
        private final String nom;
        private final String prenom;
        private final String email;
        private final String login;
        private final String roleName;
        private final boolean admin;
        private final boolean medecin;
        private final boolean secretaire;
        private final boolean active;

        private UserView(
                Long id,
                String nom,
                String prenom,
                String email,
                String login,
                String roleName,
                boolean admin,
                boolean medecin,
                boolean secretaire,
                boolean active
        ) {
            this.id = id;
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.login = login;
            this.roleName = roleName;
            this.admin = admin;
            this.medecin = medecin;
            this.secretaire = secretaire;
            this.active = active;
        }

        public static UserView from(Utilisateur user, AuthorizationService authorizationService) {
            Role role = user.getRole();
            String roleName = role != null ? role.getNom() : null;
            boolean admin = authorizationService.isAdmin(user);
            boolean medecin = authorizationService.isMedecin(user);
            boolean secretaire = authorizationService.isSecretaire(user);
            return new UserView(
                    user.getId(),
                    user.getNom(),
                    user.getPrenom(),
                    user.getEmail(),
                    user.getLogin(),
                    roleName,
                    admin,
                    medecin,
                    secretaire,
                    user.isActive()
            );
        }

        public Long getId() { return id; }
        public String getNom() { return nom; }
        public String getPrenom() { return prenom; }
        public String getEmail() { return email; }
        public String getLogin() { return login; }
        public String getRoleName() { return roleName; }
        public boolean isAdmin() { return admin; }
        public boolean isMedecin() { return medecin; }
        public boolean isSecretaire() { return secretaire; }
        public boolean isActive() { return active; }

        public String getFullName() {
            String n = nom == null ? "" : nom.trim();
            String p = prenom == null ? "" : prenom.trim();
            String full = (n + " " + p).trim();
            return full.isEmpty() ? null : full;
        }
    }
}
