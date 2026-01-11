package ma.project.dentalTech.mvc.controllers.otherModules.impl;

import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import ma.project.dentalTech.mvc.controllers.otherModules.api.UsersController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.UsersPanel;

@RequiredArgsConstructor
public class UsersControllerImpl implements UsersController {

    private JPanel cached;

    @Override
    public JPanel getView(UserPrincipal principal) {
        if (cached == null) cached = new UsersPanel(principal);
        return cached;
    }
}

