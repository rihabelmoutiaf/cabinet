package ma.project.dentalTech.service.modules.users.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO pour les informations de compte utilisateur
 */
public class UserAccountDto {

    private Long id;
    private String nom;
    private String prenom;
    private String nomComplet;
    private String email;
    private String tel;
    private String adresse;
    private String sexe;
    private String roleName;
    private boolean active;
    private LocalDate dateDerniereConnexion;
    private LocalDate dateNaissance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructeurs
    public UserAccountDto() {
    }

    public UserAccountDto(Long id, String nom, String prenom, String email, String roleName) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nomComplet = nom + " " + prenom;
        this.email = email;
        this.roleName = roleName;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
        updateNomComplet();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
        updateNomComplet();
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    private void updateNomComplet() {
        if (nom != null && prenom != null) {
            this.nomComplet = nom + " " + prenom;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "UserAccountDto{" +
                "id=" + id +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                ", roleName='" + roleName + '\'' +
                ", active=" + active +
                '}';
    }
}