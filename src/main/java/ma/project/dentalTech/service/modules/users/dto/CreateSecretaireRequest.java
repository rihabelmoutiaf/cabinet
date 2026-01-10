package ma.project.dentalTech.service.modules.users.dto;

import ma.project.dentalTech.entities.enums.Sexe;

import java.time.LocalDate;

/**
 * DTO pour la création d'une secrétaire
 */
public class CreateSecretaireRequest {

    // Informations de base (hérité de Utilisateur)
    private String nom;
    private String prenom;
    private String email;
    private String adresse;
    private String tel;
    private Sexe sexe;
    private String login;
    private String motDePasse;
    private Long roleId;

    // Informations Staff
    private String cin;
    private Double salaire;
    private Double prime;
    private LocalDate dateEmbauche;

    // Informations Secrétaire
    private String numCNSS;
    private Double commission;

    // Constructeurs
    public CreateSecretaireRequest() {
    }

    public CreateSecretaireRequest(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Double getPrime() {
        return prime;
    }

    public void setPrime(Double prime) {
        this.prime = prime;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getNumCNSS() {
        return numCNSS;
    }

    public void setNumCNSS(String numCNSS) {
        this.numCNSS = numCNSS;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    // Validation
    public boolean isValid() {
        return nom != null && !nom.trim().isEmpty() &&
                prenom != null && !prenom.trim().isEmpty() &&
                email != null && !email.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "CreateSecretaireRequest{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", numCNSS='" + numCNSS + '\'' +
                '}';
    }
}