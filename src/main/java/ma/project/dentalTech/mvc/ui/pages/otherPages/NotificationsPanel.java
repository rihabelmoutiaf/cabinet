package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class NotificationsPanel extends BasePlaceholderPanel {
    public NotificationsPanel(UserPrincipal principal) {
        super("Notifications", principal);
    }
}

