package ma.project.dentalTech.entities.agenda;


public class FileAttente {

    private Long id;
    private Long patientId;
    private int position;

    public FileAttente() {
    }

    public FileAttente(Long id, Long patientId, int position) {
        this.id = id;
        this.patientId = patientId;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
