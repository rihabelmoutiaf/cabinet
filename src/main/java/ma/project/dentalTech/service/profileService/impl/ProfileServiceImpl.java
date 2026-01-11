package ma.project.dentalTech.service.profileService.impl;

import java.util.HashMap;
import java.util.Map;
import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.entities.users.Secretaire;
import ma.project.dentalTech.entities.users.Staff;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.mvc.dto.profileDtos.ChangePasswordRequest;
import ma.project.dentalTech.mvc.dto.profileDtos.ChangePasswordResult;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileData;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileUpdateRequest;
import ma.project.dentalTech.mvc.dto.profileDtos.ProfileUpdateResult;
import ma.project.dentalTech.repository.modules.users.api.RoleRepository;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;
import ma.project.dentalTech.service.modules.auth.api.AuthService;
import ma.project.dentalTech.service.modules.auth.api.PasswordEncoder;
import ma.project.dentalTech.service.profileService.api.ProfileService;

public class ProfileServiceImpl implements ProfileService {

    private final UtilisateurRepository<Utilisateur> utilisateurRepository;
    private final RoleRepository roleRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(
            UtilisateurRepository<Utilisateur> utilisateurRepository,
            RoleRepository roleRepository,
            AuthService authService
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.authService = authService;
        this.passwordEncoder = new PasswordEncoder();
    }

    @Override
    public ProfileData loadByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        Utilisateur user = utilisateurRepository.findById(userId);
        if (user == null) {
            return null;
        }
        return toProfileData(user, resolveRole(user));
    }

    @Override
    public ProfileUpdateResult update(ProfileUpdateRequest req) {
        if (req == null || req.id() == null) {
            return ProfileUpdateResult.failure("Profil invalide", Map.of("id", "ID requis"));
        }

        Utilisateur user = utilisateurRepository.findById(req.id());
        if (user == null) {
            return ProfileUpdateResult.failure("Utilisateur introuvable", Map.of("id", "Utilisateur introuvable"));
        }

        Map<String, String> errors = new HashMap<>();
        if (req.email() != null && req.email().isBlank()) {
            errors.put("email", "Email requis");
        }
        if (!errors.isEmpty()) {
            return ProfileUpdateResult.failure("Validation invalide", errors);
        }

        if (req.prenom() != null) user.setPrenom(req.prenom());
        if (req.nom() != null) user.setNom(req.nom());
        if (req.email() != null) user.setEmail(req.email());
        if (req.adresse() != null) user.setAdresse(req.adresse());
        if (req.tel() != null) user.setTel(req.tel());
        if (req.sexe() != null) user.setSexe(req.sexe());
        if (req.dateNaissance() != null) user.setDateNaissance(req.dateNaissance());

        if (user instanceof Staff staff) {
            if (req.cin() != null) staff.setCin(req.cin());
            if (req.salaire() != null) staff.setSalaire(req.salaire());
            if (req.prime() != null) staff.setPrime(req.prime());
            if (req.dateRecrutement() != null) staff.setDateEmbauche(req.dateRecrutement());
        }

        if (user instanceof Medecin medecin) {
            if (req.specialite() != null) medecin.setSpecialite(req.specialite());
        }

        if (user instanceof Secretaire secretaire) {
            if (req.numCNSS() != null) secretaire.setNumCNSS(req.numCNSS());
            if (req.commission() != null) secretaire.setCommission(req.commission());
        }

        utilisateurRepository.update(user);

        ProfileData refreshed = toProfileData(user, resolveRole(user));
        return ProfileUpdateResult.success("Profil mis a jour", refreshed);
    }

    @Override
    public ChangePasswordResult changePassword(ChangePasswordRequest req) {
        if (req == null || req.userId() == null) {
            return ChangePasswordResult.failure("Requete invalide", Map.of("userId", "ID requis"));
        }

        Map<String, String> errors = new HashMap<>();
        if (req.currentPassword() == null || req.currentPassword().isBlank()) {
            errors.put("currentPassword", "Mot de passe actuel requis");
        }
        if (req.newPassword() == null || req.newPassword().isBlank()) {
            errors.put("newPassword", "Nouveau mot de passe requis");
        }
        if (req.confirmPassword() == null || req.confirmPassword().isBlank()) {
            errors.put("confirmPassword", "Confirmation requise");
        }
        if (!errors.isEmpty()) {
            return ChangePasswordResult.failure("Validation invalide", errors);
        }

        if (!req.newPassword().equals(req.confirmPassword())) {
            return ChangePasswordResult.failure("Les mots de passe ne correspondent pas",
                    Map.of("confirmPassword", "Confirmation invalide"));
        }

        if (authService != null && !authService.validatePasswordStrength(req.newPassword())) {
            return ChangePasswordResult.failure("Mot de passe trop faible",
                    Map.of("newPassword", "Mot de passe invalide"));
        }

        Utilisateur user = utilisateurRepository.findById(req.userId());
        if (user == null) {
            return ChangePasswordResult.failure("Utilisateur introuvable", Map.of("userId", "Utilisateur introuvable"));
        }

        if (!passwordEncoder.matches(req.currentPassword(), user.getMotDePasse())) {
            return ChangePasswordResult.failure("Mot de passe actuel incorrect",
                    Map.of("currentPassword", "Mot de passe incorrect"));
        }

        user.setMotDePasse(passwordEncoder.encode(req.newPassword()));
        utilisateurRepository.update(user);
        return ChangePasswordResult.success();
    }

    private RoleType resolveRole(Utilisateur user) {
        Role role = user.getRole();
        if (role != null && role.getNom() != null) {
            try {
                return RoleType.valueOf(role.getNom());
            } catch (IllegalArgumentException ignored) {
                return null;
            }
        }
        if (user.getRoleId() != null) {
            Role found = roleRepository.findById(user.getRoleId());
            if (found != null && found.getNom() != null) {
                try {
                    return RoleType.valueOf(found.getNom());
                } catch (IllegalArgumentException ignored) {
                    return null;
                }
            }
        }
        return null;
    }

    private ProfileData toProfileData(Utilisateur user, RoleType role) {
        Double salaire = null;
        Double prime = null;
        java.time.LocalDate dateEmbauche = null;
        String cin = null;
        if (user instanceof Staff staff) {
            salaire = staff.getSalaire();
            prime = staff.getPrime();
            dateEmbauche = staff.getDateEmbauche();
            cin = staff.getCin();
        }

        String specialite = null;
        if (user instanceof Medecin medecin) {
            specialite = medecin.getSpecialite();
        }

        String numCNSS = null;
        Double commission = null;
        if (user instanceof Secretaire secretaire) {
            numCNSS = secretaire.getNumCNSS();
            commission = secretaire.getCommission();
        }

        return ProfileData.builder()
                .id(user.getId())
                .rolePrincipal(role)
                .prenom(user.getPrenom())
                .nom(user.getNom())
                .email(user.getEmail())
                .adresse(user.getAdresse())
                .cin(cin)
                .tel(user.getTel())
                .sexe(user.getSexe())
                .login(user.getLogin())
                .lastLoginDate(user.getDateDerniereConnexion())
                .dateNaissance(user.getDateNaissance())
                .avatar(null)
                .salaire(salaire)
                .prime(prime)
                .dateRecrutement(dateEmbauche)
                .soldeConge(null)
                .cabinetId(null)
                .specialite(specialite)
                .numCNSS(numCNSS)
                .commission(commission)
                .build();
    }
}
