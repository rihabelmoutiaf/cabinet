package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CabinetPanel extends BasePlaceholderPanel {
    public CabinetPanel(UserPrincipal principal) {
        super("Cabinet courant", principal);
    }
}

