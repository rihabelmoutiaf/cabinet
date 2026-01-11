package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CaissePanel extends BasePlaceholderPanel {
    public CaissePanel(UserPrincipal principal) {
        super("Module Caisse", principal);
    }
}

