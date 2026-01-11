package ma.project.dentalTech;

import ma.project.dentalTech.configuration.ApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext.getInstance()
                .loginController()
                .showLoginView();
    }
}
