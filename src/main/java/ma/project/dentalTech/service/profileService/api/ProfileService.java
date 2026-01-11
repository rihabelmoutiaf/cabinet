package ma.project.dentalTech.service.profileService.api;

import ma.project.dentalTech.mvc.dto.profileDtos.ChangePasswordRequest;
import ma.project.dentalTech.mvc.dto.profileDtos.ChangePasswordResult;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileData;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileUpdateRequest;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileUpdateResult;

public interface ProfileService {
    ProfileData loadByUserId(Long userId);
    ProfileUpdateResult update(ProfileUpdateRequest req);
    ChangePasswordResult changePassword(ChangePasswordRequest req);
}
