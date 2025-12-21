package ma.project.dentalTech.entities.users;

import jakarta.persistence.*;
import ma.project.dentalTech.entities.base.BaseEntity;

@Entity
@Table(name = "utilisateurs")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur extends BaseEntity {

    @Column(nullable = false)
    protected String nom;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String motDePasse;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    protected Role role;

    @Column(nullable = false)
    protected boolean actif = true;

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActif() {
        return actif;
    }
}
public void setId(Long id) {
    this.id = id;
}

public void setNom(String nom) {
    this.nom = nom;
}

public void setEmail(String email) {
    this.email = email;
}

public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
}

public void setRole(Role role) {
    this.role = role;
}

public void setActif(boolean actif) {
    this.actif = actif;
}public void setId(Long id) {
    this.id = id;
}

public void setNom(String nom) {
    this.nom = nom;
}

public void setEmail(String email) {
    this.email = email;
}

public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
}

public void setActif(boolean actif) {
    this.actif = actif;
}

public void setRole(Role role) {
    this.role = role;
}
