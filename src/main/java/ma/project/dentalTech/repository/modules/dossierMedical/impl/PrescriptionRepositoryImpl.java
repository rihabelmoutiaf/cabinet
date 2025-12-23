package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Prescription;
import ma.project.dentalTech.entities.dossierMedical.Medicament;
import ma.project.dentalTech.repository.modules.dossierMedical.api.PrescriptionRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrescriptionRepositoryImpl implements PrescriptionRepository {

    private final Connection connection;

    public PrescriptionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Prescription> findAll() {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        String sql = "SELECT * FROM prescription WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(Prescription prescription) {
        String sql = "INSERT INTO prescription (patient_id, utilisateur_id, date_prescription, description, frequence, duree_en_jours, medicament_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, prescription.getPatientId());
            ps.setLong(2, prescription.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(prescription.getDatePrescription()));
            ps.setString(4, prescription.getDescription());
            ps.setInt(5, prescription.getFrequence() != null ? prescription.getFrequence() : 0);
            ps.setInt(6, prescription.getDureeEnJours() != null ? prescription.getDureeEnJours() : 0);
            ps.setObject(7, prescription.getMedicament() != null ? prescription.getMedicament().getId() : null, Types.BIGINT);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                prescription.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Prescription prescription) {
        String sql = "UPDATE prescription SET patient_id=?, utilisateur_id=?, date_prescription=?, description=?, frequence=?, duree_en_jours=?, medicament_id=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, prescription.getPatientId());
            ps.setLong(2, prescription.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(prescription.getDatePrescription()));
            ps.setString(4, prescription.getDescription());
            ps.setInt(5, prescription.getFrequence() != null ? prescription.getFrequence() : 0);
            ps.setInt(6, prescription.getDureeEnJours() != null ? prescription.getDureeEnJours() : 0);
            ps.setObject(7, prescription.getMedicament() != null ? prescription.getMedicament().getId() : null, Types.BIGINT);
            ps.setLong(8, prescription.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Prescription prescription) {
        deleteById(prescription.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM prescription WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Prescription> findByPatientId(Long patientId) {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Prescription> findByUtilisateurId(Long utilisateurId) {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription WHERE utilisateur_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Prescription> findByDatePrescriptionBetween(LocalDateTime debut, LocalDateTime fin) {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription WHERE date_prescription BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToPrescription(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Prescription mapToPrescription(ResultSet rs) throws SQLException {
        Prescription prescription = new Prescription();
        prescription.setId(rs.getLong("id"));
        prescription.setPatientId(rs.getLong("patient_id"));
        prescription.setUtilisateurId(rs.getLong("utilisateur_id"));

        Timestamp ts = rs.getTimestamp("date_prescription");
        prescription.setDatePrescription(ts != null ? ts.toLocalDateTime() : null);

        prescription.setDescription(rs.getString("description"));
        prescription.setFrequence(rs.getInt("frequence"));
        prescription.setDureeEnJours(rs.getInt("duree_en_jours"));

        // Ici, le medicament peut être chargé via MedicamentRepository si besoin
        prescription.setMedicament(null);

        prescription.setLignesMedicaments(new ArrayList<>());
        return prescription;
    }
}
