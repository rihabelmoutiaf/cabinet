package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Ordonnance;
import ma.project.dentalTech.repository.modules.dossierMedical.api.OrdonnanceRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private final Connection connection;

    public OrdonnanceRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Ordonnance> findAll() {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Ordonnance> findById(Long id) {
        String sql = "SELECT * FROM ordonnance WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(Ordonnance ordonnance) {
        String sql = "INSERT INTO ordonnance (patient_id, utilisateur_id, date_ordonnance) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, ordonnance.getPatientId());
            ps.setLong(2, ordonnance.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(ordonnance.getDateOrdonnance()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ordonnance.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Ordonnance ordonnance) {
        String sql = "UPDATE ordonnance SET patient_id=?, utilisateur_id=?, date_ordonnance=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, ordonnance.getPatientId());
            ps.setLong(2, ordonnance.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(ordonnance.getDateOrdonnance()));
            ps.setLong(4, ordonnance.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ordonnance ordonnance) {
        deleteById(ordonnance.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM ordonnance WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ordonnance> findByPatientId(Long patientId) {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Ordonnance> findByUtilisateurId(Long utilisateurId) {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance WHERE utilisateur_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Ordonnance> findByDateOrdonnanceBetween(LocalDateTime debut, LocalDateTime fin) {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM ordonnance WHERE date_ordonnance BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToOrdonnance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Ordonnance mapToOrdonnance(ResultSet rs) throws SQLException {
        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setId(rs.getLong("id"));
        ordonnance.setPatientId(rs.getLong("patient_id"));
        ordonnance.setUtilisateurId(rs.getLong("utilisateur_id"));

        Timestamp ts = rs.getTimestamp("date_ordonnance");
        ordonnance.setDateOrdonnance(ts != null ? ts.toLocalDateTime() : null);

        ordonnance.setLignesMedicaments(new ArrayList<>());
        ordonnance.setPrescriptions(new ArrayList<>());
        return ordonnance;
    }
}
