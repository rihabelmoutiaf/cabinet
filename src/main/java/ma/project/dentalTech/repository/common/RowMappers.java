package ma.project.dentalTech.repository.common;

import ma.project.dentalTech.entities.dossierMedical.*;
import ma.project.dentalTech.entities.patient.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RowMappers {

    private RowMappers() {}


    public static Patient mapPatient(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        return p;
    }

    public static DossierMedical mapDossierMedical(ResultSet rs) throws SQLException {
        DossierMedical d = new DossierMedical();
        d.setId(rs.getLong("id"));
        return d;
    }


    public static Consultation mapConsultation(ResultSet rs) throws SQLException {
        Consultation c = new Consultation();
        c.setId(rs.getLong("id"));
        return c;
    }

    public static Acte mapActe(ResultSet rs) throws SQLException {
        Acte a = new Acte();
        a.setId(rs.getLong("id"));
        return a;
    }


    public static Ordonnance mapOrdonnance(ResultSet rs) throws SQLException {
        Ordonnance o = new Ordonnance();
        o.setId(rs.getLong("id"));
        return o;
    }


    public static Prescription mapPrescription(ResultSet rs) throws SQLException {
        Prescription p = new Prescription();
        p.setId(rs.getLong("id"));
        return p;
    }


    public static SituationFinanciere mapSituationFinanciere(ResultSet rs) throws SQLException {
        SituationFinanciere s = new SituationFinanciere();
        s.setId(rs.getLong("id"));
        return s;
    }
}
