package ma.project.dentalTech.mvc.controllers.dashboardModule.api;


import javax.swing.*;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.pagesNames.ApplicationPages;

public interface DashboardController {

    void showDashboard(UserPrincipal principal);

    // callbacks venant de la vue
    JComponent onNavigateRequested(ApplicationPages page);

    void onLogoutRequested();
    void onExitRequested();
}

