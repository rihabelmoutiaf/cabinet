package ma.project.dentalTech.entities.users;

import java.time.LocalDate;

import ma.project.dentalTech.entities.base.BaseEntity;
import ma.project.dentalTech.entities.enums.Sexe;

public abstract class Utilisateur extends BaseEntity {

    protected String nom;
    protected String prenom;
    protected String email;
    protected String adresse;
    protected String tel;
    protected Sexe sexe;
    protected String login;
    protected String motDePasse;
    protected LocalDate dateDerniereConnexion;
    protected LocalDate dateNaissance;
    protected Role role;
    protected Long roleId;

    public Utilisateur() {
        super();
    }

    public Utilisateur(Long id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    /* ================= GETTERS & SETTERS ================= */

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

    public LocalDate getDateDerniereConnexion() {
        return dateDerniereConnexion;
    }

    public void setDateDerniereConnexion(LocalDate dateDerniereConnexion) {
        this.dateDerniereConnexion = dateDerniereConnexion;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
