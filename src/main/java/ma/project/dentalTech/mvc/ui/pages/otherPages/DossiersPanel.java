package ma.project.dentalTech.mvc.ui.pages.otherPages;

import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.ui.pages.otherPages.common.BasePlaceholderPanel;

public class DossiersPanel extends BasePlaceholderPanel {
    public DossiersPanel(UserPrincipal principal) {
        super("Dossiers Médicaux", principal);
    }
}

