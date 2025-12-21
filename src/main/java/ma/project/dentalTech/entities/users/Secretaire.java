package ma.project.dentalTech.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "secretaires")
public class Secretaire extends Utilisateur {

    public Secretaire() {}
}
