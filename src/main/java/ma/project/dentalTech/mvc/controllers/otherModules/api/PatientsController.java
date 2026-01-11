package ma.project.dentalTech.mvc.controllers.otherModules.api;

import javax.swing.JPanel;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;

public interface PatientsController {
    JPanel getView(UserPrincipal principal);
}



