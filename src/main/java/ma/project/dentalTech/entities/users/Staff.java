package ma.project.dentalTech.entities.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff extends Utilisateur {

    public Staff() {}
}
