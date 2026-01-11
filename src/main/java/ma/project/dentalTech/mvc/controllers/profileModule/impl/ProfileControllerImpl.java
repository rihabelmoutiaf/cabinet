package ma.project.dentalTech.mvc.controllers.profileModule.impl;



import java.util.function.Consumer;
import javax.swing.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.project.dentalTech.mvc.controllers.profileModule.api.ProfileController;
import ma.project.dentalTech.mvc.dto.authentificationDtos.UserPrincipal;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileData;
import ma.project.dentalTech.mvc.ui.pages.profilePages.ProfilePanel;
import ma.project.dentalTech.service.profileService.api.ProfileService;

@Getter @Setter
@AllArgsConstructor
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService service;

    @Override
    public JPanel getView(UserPrincipal principal) {
        return getView(principal, null);


    }

    @Override
    public JPanel getView(UserPrincipal principal, Consumer<ProfileData> onProfileSaved) {
        if (principal == null || principal.id() == null) {
            return new JPanel(); // ou panel "Session invalide"
        }
        ProfileData data = service.loadByUserId(principal.id());

        // ✅ IMPORTANT : ProfilePanel doit accepter ce callback
        return new ProfilePanel(this, service, data, onProfileSaved);
    }
}

