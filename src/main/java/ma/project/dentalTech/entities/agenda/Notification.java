package ma.project.dentalTech.entities.agenda;


import java.time.LocalDateTime;

public class Notification {

    private Long id;
    private Long userId;

    private String message;
    private boolean lue;

    private LocalDateTime dateEnvoi;

    public Notification() {
    }

    public Notification(Long id, Long userId, String message, LocalDateTime dateEnvoi) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
        this.lue = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public LocalDateTime getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(LocalDateTime dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
}
