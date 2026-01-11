package ma.project.dentalTech.mvc.controllers.authentificationModule.impl;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.project.dentalTech.common.consoleLog.ConsoleLogger;
import ma.project.dentalTech.configuration.ApplicationContext;
import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.mvc.bridge.AuthServiceAdapter;
import ma.project.dentalTech.mvc.controllers.dashboardModule.api.DashboardController;
import ma.project.dentalTech.mvc.controllers.authentificationModule.api.LoginController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.AuthResult;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.frames.LoginUI;
import ma.project.dentalTech.mvc.ui.palette.alert.Alert;
import ma.project.dentalTech.security.Privileges;


@Getter @Setter
@AllArgsConstructor
public class LoginControllerImpl implements LoginController {

   private final AuthServiceAdapter authServiceAdapter;
   private LoginUI view;

    public LoginControllerImpl() {
        this(ApplicationContext.getInstance().authServiceAdapter());
    }

    public LoginControllerImpl(AuthServiceAdapter authServiceAdapter) {
        this.authServiceAdapter = authServiceAdapter;
    }

    @Override
    public void showLoginView() {
        ConsoleLogger.info("Call for Login View...");
        // EDT Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            if (view == null) view = new LoginUI(this);
            view.clearErrors();
            view.setVisible(true);
        });
    }

    @Override
    public void onLoginRequested(String login, String password) {

        String lg = (login == null) ? "" : login.trim();
        String pw = (password == null) ? "" : password;

        AuthResult result = authenticate(lg, pw);

        // 1 - erreurs de validation (formulaire)
        if (!result.getFieldErrors().isEmpty()) {
            view.showFieldErrors(result.getFieldErrors());
            return;
        }

        // 2 - échec métier (user inexistant / mdp incorrect)
        if (!result.isSuccess() || result.getPrincipal() == null) {
            Alert.error(view, result.getMessage());
            return;
        }

        // 3 - succès
        UserPrincipal principal = result.getPrincipal();

        Alert.success(view, "Bienvenue " + principal.nom() + " ^_^");

        view.dispose();
        onLoginSuccess(principal);

    }

    @Override
    public void onCancelRequested() {
        if (Alert.confirm(view, "Voulez-vous quitter ?")) {
            view.dispose();
            Window window = SwingUtilities.getWindowAncestor(view);
            if (window != null) window.dispose();
        }
    }

    @Override
    public void onLoginSuccess(UserPrincipal principal) {

        // TODO: ouvrir Dashboard / MainFrame
        ConsoleLogger.log("LOGIN SUCCESS: " + principal.nom() + " => [ username : " + principal.login() + " ]");

        // Ouvre un seul dashboard qui masque/affiche les modules
        ConsoleLogger.info("Call for Dashboard View : ");
        SwingUtilities.invokeLater(() -> {

            var dashboardController = ApplicationContext.getInstance().dashboardController();
            dashboardController.showDashboard(principal);
        });

    }

    private AuthResult authenticate(String login, String password) {
        Map<String, String> errors = new HashMap<>();
        if (login == null || login.isBlank()) {
            errors.put("login", "Login requis");
        }
        if (password == null || password.isBlank()) {
            errors.put("password", "Mot de passe requis");
        }
        if (!errors.isEmpty()) {
            return AuthResult.failure("Validation invalide", errors);
        }

        AuthServiceAdapter.UserView user = authServiceAdapter.login(login, password);
        if (user == null) {
            return AuthResult.failure("Login ou mot de passe incorrect");
        }

        return AuthResult.success(toPrincipal(user));
    }

    private UserPrincipal toPrincipal(AuthServiceAdapter.UserView user) {
        RoleType role = resolveRole(user);
        Set<RoleType> roles = new HashSet<>();
        if (role != null) {
            roles.add(role);
        }
        Set<String> privileges = Privileges.forRole(role);

        String fullName = user.getFullName();
        if (fullName == null || fullName.isBlank()) {
            fullName = user.getLogin() != null ? user.getLogin() : "-";
        }

        return new UserPrincipal(
                user.getId(),
                fullName,
                user.getEmail(),
                user.getLogin(),
                role,
                roles,
                privileges
        );
    }

    private RoleType resolveRole(AuthServiceAdapter.UserView user) {
        if (user.isAdmin()) {
            return RoleType.ADMIN;
        }
        if (user.isMedecin()) {
            return RoleType.MEDECIN;
        }
        if (user.isSecretaire()) {
            return RoleType.SECRETAIRE;
        }
        return RoleType.STAFF;
    }
}


