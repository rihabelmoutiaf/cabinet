package ma.project.dentalTech.mvc.controllers.profileModule.api;



import java.util.function.Consumer;
import javax.swing.*;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileData;

public interface ProfileController {
    JPanel getView(UserPrincipal principal);
    JPanel getView(UserPrincipal principal, Consumer<ProfileData> onProfileSaved);
}

