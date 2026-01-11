package ma.project.dentalTech.mvc.controllers.otherModules.impl;

import javax.swing.JPanel;
import lombok.RequiredArgsConstructor;
import ma.project.dentalTech.mvc.controllers.otherModules.api.PatientsController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.PatientsPanel;

@RequiredArgsConstructor
public class PatientsControllerImpl implements PatientsController {

    private JPanel cached;

    @Override
    public JPanel getView(UserPrincipal principal) {
        if (cached == null) {
            cached = new PatientsPanel(principal);
        }
        return cached;
    }
}

