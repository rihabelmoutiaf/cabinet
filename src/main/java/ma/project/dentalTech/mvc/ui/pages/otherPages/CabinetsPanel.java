package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class CabinetsPanel extends BasePlaceholderPanel {
    public CabinetsPanel(UserPrincipal principal) {
        super("Backoffice — Cabinets", principal);
    }
}

