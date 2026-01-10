package ma.project.dentalTech.service.modules.users.dto;

import ma.project.dentalTech.entities.enums.Sexe;

/**
 * DTO pour la création d'un administrateur
 */
public class CreateAdminRequest {

    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String tel;
    private Sexe sexe;
    private String login;
    private String motDePasse;
    private Long roleId;

    // Constructeurs
    public CreateAdminRequest() {
    }

    public CreateAdminRequest(String nom, String prenom, String email, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    // Validation
    public boolean isValid() {
        return nom != null && !nom.trim().isEmpty() &&
                prenom != null && !prenom.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                login != null && !login.trim().isEmpty() &&
                motDePasse != null && !motDePasse.isEmpty();
    }

    @Override
    public String toString() {
        return "CreateAdminRequest{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                '}';
    }
}