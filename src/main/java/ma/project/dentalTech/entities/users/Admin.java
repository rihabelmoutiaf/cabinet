package ma.project.dentalTech.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Utilisateur {

    public Admin() {}

    public void setEmail(String email) {
    }

    public void setNom(String nom) {
    }

    public void setId(long id) {
    }

    public void setMotDePasse(String motDePasse) {
    }

    public void setActif(boolean actif) {
    }
}
