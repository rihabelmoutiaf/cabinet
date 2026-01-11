package ma.project.dentalTech.mvc.controllers.authentificationModule.api;


import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;

public interface LoginController {

    void showLoginView();

    // callbacks venant de la vue
    void onLoginRequested(String login, String password);
    void onCancelRequested();

    void onLoginSuccess(UserPrincipal principal);
}

