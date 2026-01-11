package ma.project.dentalTech.mvc.controllers.otherModules.impl;

import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import ma.project.dentalTech.mvc.controllers.otherModules.api.DossiersController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.DossiersPanel;

@RequiredArgsConstructor
public class DossiersControllerImpl implements DossiersController {

    private JPanel cached;

    @Override
    public JPanel getView(UserPrincipal principal) {
        if (cached == null) cached = new DossiersPanel(principal);
        return cached;
    }
}

