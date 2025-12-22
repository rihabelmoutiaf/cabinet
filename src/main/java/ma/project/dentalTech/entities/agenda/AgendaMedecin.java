package ma.project.dentalTech.entities.agenda;


import java.time.LocalDate;
import java.time.LocalTime;

public class AgendaMedecin {

    private Long id;
    private Long medecinId;

    private LocalDate date;

    private LocalTime heureDebut;
    private LocalTime heureFin;

    private boolean disponible;

    public AgendaMedecin() {
    }

    public AgendaMedecin(Long id, Long medecinId, LocalDate date) {
        this.id = id;
        this.medecinId = medecinId;
        this.date = date;
        this.disponible = true;
    }

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
