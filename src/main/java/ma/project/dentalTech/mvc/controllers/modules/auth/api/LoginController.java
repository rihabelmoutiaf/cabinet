package ma.project.dentalTech.mvc.controllers.modules.auth.api;

import ma.project.dentalTech.mvc.bridge.AuthServiceAdapter;

public interface LoginController {

    AuthServiceAdapter.UserView login(String login, String password);

    void logout();

    boolean isAuthenticated();

    AuthServiceAdapter.UserView currentUser();
}
