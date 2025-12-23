package ma.project.dentalTech.repository.modules.patient.impl;

import ma.project.dentalTech.entities.patient.Patient;
import ma.project.dentalTech.entities.enums.Assurance;
import ma.project.dentalTech.entities.enums.Sexe;
import ma.project.dentalTech.repository.modules.patient.api.PatientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    private Connection connection;

    public PatientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Patient save(Patient patient) {
        String sql = """
            INSERT INTO patient
            (nom, prenom, adresse, telephone, email, date_naissance, date_creation, sexe, assurance)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getAdresse());
            ps.setString(4, patient.getTelephone());
            ps.setString(5, patient.getEmail());
            ps.setDate(6, Date.valueOf(patient.getDateNaissance()));
            ps.setTimestamp(7, Timestamp.valueOf(patient.getDateCreation()));
            ps.setString(8, patient.getSexe().name());
            ps.setString(9, patient.getAssurance().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                patient.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public Patient findById(Long id) {
        String sql = "SELECT * FROM patient WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToPatient(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY date_creation DESC";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToPatient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Patient> findByNom(String nom) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE nom LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToPatient(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Patient patient) {
        String sql = """
            UPDATE patient SET
            nom = ?, prenom = ?, adresse = ?, telephone = ?, email = ?,
            date_naissance = ?, sexe = ?, assurance = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setString(3, patient.getAdresse());
            ps.setString(4, patient.getTelephone());
            ps.setString(5, patient.getEmail());
            ps.setDate(6, Date.valueOf(patient.getDateNaissance()));
            ps.setString(7, patient.getSexe().name());
            ps.setString(8, patient.getAssurance().name());
            ps.setLong(9, patient.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM patient WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Patient mapToPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getLong("id"));
        patient.setNom(rs.getString("nom"));
        patient.setPrenom(rs.getString("prenom"));
        patient.setAdresse(rs.getString("adresse"));
        patient.setTelephone(rs.getString("telephone"));
        patient.setEmail(rs.getString("email"));
        patient.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
        patient.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());
        patient.setSexe(Sexe.valueOf(rs.getString("sexe")));
        patient.setAssurance(Assurance.valueOf(rs.getString("assurance")));
        return patient;
    }
}
