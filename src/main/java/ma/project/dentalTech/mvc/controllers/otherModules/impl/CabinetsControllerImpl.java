package ma.project.dentalTech.mvc.controllers.otherModules.impl;

import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import ma.project.dentalTech.mvc.controllers.otherModules.api.CabinetsController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.CabinetsPanel;

@RequiredArgsConstructor
public class CabinetsControllerImpl implements CabinetsController {

    private JPanel cached;

    @Override
    public JPanel getView(UserPrincipal principal) {
        if (cached == null) cached = new CabinetsPanel(principal);
        return cached;
    }
}

