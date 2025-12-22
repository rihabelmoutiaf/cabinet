package ma.project.dentalTech.service.modules.auth.api;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service pour encoder et vérifier les mots de passe avec BCrypt
 */
public class PasswordEncoder {

    private static final int SALT_ROUNDS = 12;

    /**
     * Encode un mot de passe en clair
     * @param plainPassword mot de passe en clair
     * @return mot de passe hashé
     */
    public String encode(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être vide");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(SALT_ROUNDS));
    }

    /**
     * Vérifie si un mot de passe correspond au hash
     * @param plainPassword mot de passe en clair
     * @param hashedPassword mot de passe hashé
     * @return true si le mot de passe correspond
     */
    public boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Vérifie si un hash est valide
     * @param hashedPassword hash à vérifier
     * @return true si le hash est valide
     */
    public boolean isValidHash(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        try {
            // BCrypt hash commence toujours par $2a$, $2b$, ou $2y$
            return hashedPassword.matches("^\\$2[aby]\\$\\d{2}\\$.{53}$");
        } catch (Exception e) {
            return false;
        }
    }
}