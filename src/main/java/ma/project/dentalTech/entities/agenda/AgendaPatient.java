package ma.project.dentalTech.entities.agenda;
import java.time.LocalDate;
import java.time.LocalTime;

public class AgendaPatient {

    private Long id;
    private Long patientId;
    private Long rdvId;

    private LocalDate date;
    private LocalTime heure;

    public AgendaPatient() {
    }

    public AgendaPatient(Long id, Long patientId, Long rdvId, LocalDate date, LocalTime heure) {
        this.id = id;
        this.patientId = patientId;
        this.rdvId = rdvId;
        this.date = date;
        this.heure = heure;
    }



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

    public Long getRdvId() {
        return rdvId;
    }

    public void setRdvId(Long rdvId) {
        this.rdvId = rdvId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }
}
