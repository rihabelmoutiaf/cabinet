package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class PatientsPanel extends BasePlaceholderPanel {
    public PatientsPanel(UserPrincipal principal) {
        super("Module Patients", principal);
    }
}

