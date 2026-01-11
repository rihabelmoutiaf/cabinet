package ma.project.dentalTech.mvc.controllers.dashboardModule.impl;

import lombok.Getter;
import lombok.Setter;
import ma.project.dentalTech.common.consoleLog.ConsoleLogger;
import ma.project.dentalTech.configuration.ApplicationContext;
import ma.project.dentalTech.mvc.controllers.dashboardModule.api.DashboardController;
import ma.project.dentalTech.mvc.controllers.authentificationModule.api.LoginController;
import ma.project.dentalTech.mvc.controllers.otherModules.api.*;
import ma.project.dentalTech.mvc.controllers.profileModule.api.ProfileController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.dashboardPages.pageFactory.DashboardPanelFactory;
import ma.project.dentalTech.mvc.ui.frames.DashboardUI;
import ma.project.dentalTech.mvc.ui.pages.pagesNames.ApplicationPages;
import ma.project.dentalTech.mvc.ui.pages.otherPages.CabinetPanel;
import ma.project.dentalTech.mvc.ui.pages.otherPages.NotificationsPanel;
import ma.project.dentalTech.mvc.ui.palette.alert.Alert;
import ma.project.dentalTech.service.authentificationService.api.AuthorizationService;

import javax.swing.*;
import java.awt.*;

import static ma.project.dentalTech.security.Privileges.*;

@Getter  @Setter
public class DashboardControllerImpl implements DashboardController {

    private final AuthorizationService authorizationService;
    private final LoginController loginController;

    private DashboardUI view;
    private UserPrincipal principal;

    public DashboardControllerImpl(AuthorizationService authorizationService, LoginController loginController) {
        this.authorizationService = authorizationService;
        this.loginController = loginController;
    }

    @Override
    public void showDashboard(UserPrincipal principal) {
        this.principal = principal;

        ConsoleLogger.info("Call for Dashboard View...");

        Runnable show = () -> {
            if (view != null) view.dispose();
            view = new DashboardUI(this, authorizationService, principal);
            view.setVisible(true);
            view.navigateTo(ApplicationPages.DASHBOARD);
        };

        if (SwingUtilities.isEventDispatchThread()) show.run();
        else SwingUtilities.invokeLater(show);
    }

    // ✅ clé : retourne un panel à injecter dans le CenterPanel
    @Override
    public JComponent onNavigateRequested(ApplicationPages page) {
        if (principal == null || page == null) return empty("Page invalide.");

        if (!canAccess(page)) {
            if (view != null) Alert.error(view, "Accès refusé.");
            return forbidden("Accès refusé.");
        }

        // ✅ délégation : récupère le bon controller depuis ApplicationContext
        return switch (page) {
            case DASHBOARD -> DashboardPanelFactory.create(principal);

            case PATIENTS ->
                    ApplicationContext.getInstance().patientsController().getView(principal);

            case CAISSE ->
                    ApplicationContext.getInstance().caisseController().getView(principal);

            case USERS ->
                    ApplicationContext.getInstance().usersController().getView(principal);

            case CABINETS ->
                    ApplicationContext.getInstance().cabinetsController().getView(principal);

            case DOSSIERS_MEDICAUX ->
                    ApplicationContext.getInstance().dossiersController().getView(principal);

            case PARAMETRAGE ->
                    ApplicationContext.getInstance().parametrageController().getView(principal);

            case PROFILE -> {
                var controller = ApplicationContext.getInstance().profileController();
                yield controller.getView(principal, updatedProfile -> {
                    if (view != null) {
                        SwingUtilities.invokeLater(() -> view.refreshHeaderFromProfile(updatedProfile));
                    }
                });
            }


            case NOTIFICATIONS ->
                    new NotificationsPanel(principal);

            case CABINET -> new CabinetPanel(principal);

        };
    }


    private JComponent forbidden(String msg) {
        JPanel p = new JPanel(new GridBagLayout());
        JLabel lbl = new JLabel(msg);
        lbl.setFont(new Font("Optima", Font.BOLD, 22));
        p.add(lbl);
        return p;
    }

    private JComponent empty(String msg) {
        JPanel p = new JPanel(new GridBagLayout());
        JLabel lbl = new JLabel(msg);
        lbl.setFont(new Font("Optima", Font.PLAIN, 18));
        p.add(lbl);
        return p;
    }

    private boolean canAccess(ApplicationPages page) {
        if (principal == null) return false;

        return switch (page) {
            case DASHBOARD -> true;

            case PATIENTS ->
                    authorizationService.hasPrivilege(principal, PATIENT_ACCESS)
                    || authorizationService.hasPrivilege(principal, DOSSIER_ACCESS);

            case CAISSE ->
                    authorizationService.hasPrivilege(principal, CAISSE_ACCESS);

            case USERS ->
                    authorizationService.hasPrivilege(principal, USERS_ACCESS);

            case CABINETS ->
                    authorizationService.hasPrivilege(principal, CABINET_ACCESS);

            case DOSSIERS_MEDICAUX ->
                    authorizationService.hasPrivilege(principal, DOSSIER_ACCESS);

            case PARAMETRAGE ->
                    authorizationService.hasPrivilege(principal, CABINET_ACCESS);

            // ce sont des pages “générales” accessibles (vous pouvez les restreindre plus tard).
            case PROFILE, NOTIFICATIONS, CABINET -> true;

        };
    }

    @Override
    public void onLogoutRequested() {
        if (view == null) return;

        if (!Alert.confirm(view, "Confirmer la déconnexion ?")) return;

        ConsoleLogger.info("LOGOUT: " + (principal != null ? principal.login() : "—"));

        view.dispose();
        view = null;
        principal = null;

        Runnable showLogin = () -> loginController.showLoginView();
        if (SwingUtilities.isEventDispatchThread()) showLogin.run();
        else SwingUtilities.invokeLater(showLogin);
    }

    @Override
    public void onExitRequested() {
        if (view == null) return;

        if (Alert.confirm(view, "Quitter l'application ?")) {
            System.exit(0);
        }
    }
}


