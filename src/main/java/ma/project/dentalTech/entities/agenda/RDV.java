package ma.project.dentalTech.entities.agenda;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import ma.project.dentalTech.entities.enums.StatutRDV;

public class RDV {

    private Long id;
    private Long patientId;
    private Long medecinId;

    private LocalDateTime dateHeure;
    private StatutRDV statut;

    private String motif;
    private String noteRdvMedecin;

    public RDV() {
    }

    public RDV(Long id, Long patientId, Long medecinId, LocalDateTime dateHeure) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.dateHeure = dateHeure;
        this.statut = StatutRDV.PLANIFIE;
    }

    // 🔹 Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public StatutRDV getStatut() {
        return statut;
    }

    public void setStatut(StatutRDV statut) {
        this.statut = statut;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNoteRdvMedecin() {
        return noteRdvMedecin;
    }

    public void setNoteRdvMedecin(String noteRdvMedecin) {
        this.noteRdvMedecin = noteRdvMedecin;
    }

    // 🔹 Helpers (comme dans ton style)

    public LocalDate getDate() {
        return dateHeure != null ? dateHeure.toLocalDate() : null;
    }

    public LocalTime getHeure() {
        return dateHeure != null ? dateHeure.toLocalTime() : null;
    }

    public void setDate(LocalDate date) {

    }

    public void setHeure(LocalTime heure) {

    }
}
