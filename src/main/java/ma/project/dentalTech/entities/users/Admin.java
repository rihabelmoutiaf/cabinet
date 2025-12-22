package ma.project.dentalTech.entities.users;

import ma.project.dentalTech.entities.enums.Sexe;

/**
 * Entité Admin
 * Hérite de Utilisateur
 */
public class Admin extends Utilisateur {

    /* ================= CONSTRUCTEURS ================= */

    public Admin() {
        super();
    }

    public Admin(Long id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Admin(Long id, String nom, String prenom, String email,
                 String login, String motDePasse, Sexe sexe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
        this.motDePasse = motDePasse;
        this.sexe = sexe;
    }

    public Admin(Long id, String nom, String prenom, String email,
                 String adresse, String tel, Sexe sexe,
                 String login, String motDePasse,
                 Long roleId) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.sexe = sexe;
        this.login = login;
        this.motDePasse = motDePasse;
        this.roleId = roleId;
    }

    /* ================= MÉTHODES MÉTIER (optionnel) ================= */

    public boolean isAdmin() {
        return true;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", active=" + active +
                '}';
    }
}
