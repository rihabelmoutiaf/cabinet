package ma.project.dentalTech.mvc.controllers.otherModules.impl;

import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import ma.project.dentalTech.mvc.controllers.otherModules.api.CaisseController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.CaissePanel;

@RequiredArgsConstructor
public class CaisseControllerImpl implements CaisseController {

    private JPanel cached;

    @Override
    public JPanel getView(UserPrincipal principal) {
        if (cached == null) cached = new CaissePanel(principal);
        return cached;
    }
}

