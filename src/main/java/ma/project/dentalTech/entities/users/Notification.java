package ma.project.dentalTech.entities.users;

import java.time.LocalDateTime;

import ma.project.dentalTech.entities.base.BaseEntity;
import ma.project.dentalTech.entities.enums.PrioriteNotification;
import ma.project.dentalTech.entities.enums.TypeNotification;
import ma.project.dentalTech.entities.enums.TitreNotification;

public class Notification extends BaseEntity {

    private String message;
    private TypeNotification type;
    private PrioriteNotification priorite;
    private TitreNotification titre;
    private boolean lu;

    private Long utilisateurId;
    private Utilisateur utilisateur;

    public Notification() {
        super();
        this.lu = false;
    }

    public Notification(String message, TypeNotification type,
                        PrioriteNotification priorite, TitreNotification titre,
                        Long utilisateurId) {
        this();
        this.message = message;
        this.type = type;
        this.priorite = priorite;
        this.titre = titre;
        this.utilisateurId = utilisateurId;
    }

    /* ================= GETTERS & SETTERS ================= */

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TypeNotification getType() {
        return type;
    }

    public void setType(TypeNotification type) {
        this.type = type;
    }

    public PrioriteNotification getPriorite() {
        return priorite;
    }

    public void setPriorite(PrioriteNotification priorite) {
        this.priorite = priorite;
    }

    public TitreNotification getTitre() {
        return titre;
    }

    public void setTitre(TitreNotification titre) {
        this.titre = titre;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
