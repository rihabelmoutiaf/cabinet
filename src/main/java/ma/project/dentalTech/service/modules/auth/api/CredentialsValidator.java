package ma.project.dentalTech.service.modules.auth.api;

import java.util.regex.Pattern;

/**
 * Service de validation des credentials (login, email, mot de passe)
 */
public class CredentialsValidator {

    // Patterns de validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern LOGIN_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._-]{3,50}$"
    );

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"
    );

    /**
     * Valide une adresse email
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Valide un login (username)
     */
    public boolean isValidLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            return false;
        }
        String trimmed = login.trim();
        return trimmed.length() >= 3 &&
                trimmed.length() <= 50 &&
                LOGIN_PATTERN.matcher(trimmed).matches();
    }

    /**
     * Valide la force d'un mot de passe
     * Règles:
     * - Au moins 8 caractères
     * - Au moins une majuscule
     * - Au moins une minuscule
     * - Au moins un chiffre
     * - Au moins un caractère spécial (@$!%*?&)
     */
    public boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        return password.length() >= 8 && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Valide un mot de passe avec des règles personnalisées
     */
    public PasswordValidationResult validatePasswordDetailed(String password) {
        PasswordValidationResult result = new PasswordValidationResult();

        if (password == null || password.isEmpty()) {
            result.setValid(false);
            result.addError("Le mot de passe ne peut pas être vide");
            return result;
        }

        if (password.length() < 8) {
            result.addError("Le mot de passe doit contenir au moins 8 caractères");
        }

        if (!password.matches(".*[A-Z].*")) {
            result.addError("Le mot de passe doit contenir au moins une majuscule");
        }

        if (!password.matches(".*[a-z].*")) {
            result.addError("Le mot de passe doit contenir au moins une minuscule");
        }

        if (!password.matches(".*\\d.*")) {
            result.addError("Le mot de passe doit contenir au moins un chiffre");
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            result.addError("Le mot de passe doit contenir au moins un caractère spécial (@$!%*?&)");
        }

        result.setValid(result.getErrors().isEmpty());
        return result;
    }

    /**
     * Valide un numéro de téléphone marocain
     */
    public boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String cleaned = phone.replaceAll("[\\s-]", "");

        // Format marocain: 06/07 + 8 chiffres OU +212 6/7 + 8 chiffres
        return cleaned.matches("^0[67]\\d{8}$") ||
                cleaned.matches("^\\+2126\\d{8}$") ||
                cleaned.matches("^\\+2127\\d{8}$");
    }

    /**
     * Valide un CIN marocain
     */
    public boolean isValidCIN(String cin) {
        if (cin == null || cin.trim().isEmpty()) {
            return false;
        }

        String cleaned = cin.toUpperCase().trim();

        // Format: 1-2 lettres + 6-7 chiffres
        return cleaned.matches("^[A-Z]{1,2}\\d{6,7}$");
    }

    /**
     * Classe interne pour les résultats de validation détaillés
     */
    public static class PasswordValidationResult {
        private boolean valid = true;
        private java.util.List<String> errors = new java.util.ArrayList<>();

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public java.util.List<String> getErrors() {
            return errors;
        }

        public void addError(String error) {
            this.errors.add(error);
        }

        public String getErrorMessage() {
            return String.join(", ", errors);
        }
    }
}