package ma.project.dentalTech.mvc.controllers.modules.auth.impl;

import ma.project.dentalTech.configuration.ApplicationContext;
import ma.project.dentalTech.mvc.bridge.AuthServiceAdapter;
import ma.project.dentalTech.mvc.controllers.modules.auth.api.LoginController;

public class LoginControllerImpl implements LoginController {

    private final AuthServiceAdapter authServiceAdapter;

    public LoginControllerImpl() {
        this(ApplicationContext.getInstance().authServiceAdapter());
    }

    public LoginControllerImpl(AuthServiceAdapter authServiceAdapter) {
        this.authServiceAdapter = authServiceAdapter;
    }

    @Override
    public AuthServiceAdapter.UserView login(String login, String password) {
        return authServiceAdapter.login(login, password);
    }

    @Override
    public void logout() {
        authServiceAdapter.logout();
    }

    @Override
    public boolean isAuthenticated() {
        return authServiceAdapter.isAuthenticated();
    }

    @Override
    public AuthServiceAdapter.UserView currentUser() {
        return authServiceAdapter.currentUser();
    }
}
