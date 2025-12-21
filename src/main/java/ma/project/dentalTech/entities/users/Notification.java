package ma.project.dentalTech.entities.users;

import jakarta.persistence.*;
import ma.project.dentalTech.entities.base.BaseEntity;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(nullable = false)
    private boolean lu = false;

    public String getMessage() {
        return message;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }
}
