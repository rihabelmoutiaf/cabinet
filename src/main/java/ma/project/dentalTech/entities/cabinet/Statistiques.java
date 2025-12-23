package ma.project.dentalTech.entities.cabinet;

import java.time.LocalDate;
import java.util.List;

public class Statistiques {



    private Long cabinetId;
    private LocalDate dateDebut;
    private LocalDate dateFin;


    private Double totalCharges;
    private Double totalRevenues;
    private Double benefice;

    private int nombreCharges;
    private int nombreRevenues;



    public Statistiques() {
        this.totalCharges = 0.0;
        this.totalRevenues = 0.0;
        this.benefice = 0.0;
    }

    public Statistiques(Long cabinetId, LocalDate dateDebut, LocalDate dateFin) {
        this();
        this.cabinetId = cabinetId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public void calculer(List<Charges> charges, List<Revenues> revenues) {

        totalCharges = charges.stream()
                .mapToDouble(c -> c.getMontant() != null ? c.getMontant() : 0.0)
                .sum();

        totalRevenues = revenues.stream()
                .mapToDouble(r -> r.getMontant() != null ? r.getMontant() : 0.0)
                .sum();

        nombreCharges = charges.size();
        nombreRevenues = revenues.size();

        benefice = totalRevenues - totalCharges;
    }



    public Long getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Long cabinetId) {
        this.cabinetId = cabinetId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public Double getTotalRevenues() {
        return totalRevenues;
    }

    public void setTotalRevenues(Double totalRevenues) {
        this.totalRevenues = totalRevenues;
    }

    public Double getBenefice() {
        return benefice;
    }

    public void setBenefice(Double benefice) {
        this.benefice = benefice;
    }

    public int getNombreCharges() {
        return nombreCharges;
    }

    public void setNombreCharges(int nombreCharges) {
        this.nombreCharges = nombreCharges;
    }

    public int getNombreRevenues() {
        return nombreRevenues;
    }

    public void setNombreRevenues(int nombreRevenues) {
        this.nombreRevenues = nombreRevenues;
    }

    @Override
    public String toString() {
        return "Statistiques{" +
                "cabinetId=" + cabinetId +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", totalCharges=" + totalCharges +
                ", totalRevenues=" + totalRevenues +
                ", benefice=" + benefice +
                '}';
    }
}
