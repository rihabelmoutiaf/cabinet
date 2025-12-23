package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.DossierMedical;
import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.repository.modules.dossierMedical.api.DossierMedicalRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DossierMedicalRepositoryImpl implements DossierMedicalRepository {

    private final Connection connection;

    public DossierMedicalRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DossierMedical> findAll() {
        List<DossierMedical> list = new ArrayList<>();
        String sql = "SELECT * FROM dossier_medical";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToDossierMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<DossierMedical> findById(Long id) {
        String sql = "SELECT * FROM dossier_medical WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToDossierMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(DossierMedical dossierMedical) {
        String sql = "INSERT INTO dossier_medical (patient_id, date_creation, notes_initiales) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, dossierMedical.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(dossierMedical.getDateCreation()));
            ps.setString(3, dossierMedical.getNotesInitiales());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                dossierMedical.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DossierMedical dossierMedical) {
        String sql = "UPDATE dossier_medical SET patient_id=?, date_creation=?, notes_initiales=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, dossierMedical.getPatientId());
            ps.setTimestamp(2, Timestamp.valueOf(dossierMedical.getDateCreation()));
            ps.setString(3, dossierMedical.getNotesInitiales());
            ps.setLong(4, dossierMedical.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DossierMedical dossierMedical) {
        deleteById(dossierMedical.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM dossier_medical WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<DossierMedical> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM dossier_medical WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToDossierMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private DossierMedical mapToDossierMedical(ResultSet rs) throws SQLException {
        DossierMedical dossier = new DossierMedical();
        dossier.setId(rs.getLong("id"));
        dossier.setPatientId(rs.getLong("patient_id"));

        Timestamp ts = rs.getTimestamp("date_creation");
        dossier.setDateCreation(ts != null ? ts.toLocalDateTime() : null);

        dossier.setNotesInitiales(rs.getString("notes_initiales"));


        dossier.setConsultations(new ArrayList<>());

        return dossier;
    }
}
