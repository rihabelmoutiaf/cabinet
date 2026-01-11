package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class ParametragePanel extends BasePlaceholderPanel {
    public ParametragePanel(UserPrincipal principal) {
        super("Paramétrage Cabinet", principal);
    }
}

