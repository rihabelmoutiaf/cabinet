package ma.project.dentalTech.mvc.dto.authentificationDtos;


public record AuthRequest(
        String login,
        String password
) {}

