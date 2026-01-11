package ma.project.dentalTech.mvc.dto.profileDtos;


import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        Long userId,
        String currentPassword,
        String newPassword,
        String confirmPassword
) {}

