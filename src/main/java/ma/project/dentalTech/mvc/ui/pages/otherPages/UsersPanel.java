package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class UsersPanel extends BasePlaceholderPanel {
    public UsersPanel(UserPrincipal principal) {
        super("Backoffice — Users", principal);
    }
}

