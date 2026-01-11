package ma.project.dentalTech.mvc.controllers.otherModules.api;

import javax.swing.*;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;

public interface ParametrageController {
    JPanel getView(UserPrincipal principal);
}

