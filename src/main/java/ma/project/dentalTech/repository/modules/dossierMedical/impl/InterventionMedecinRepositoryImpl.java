package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.InterventionMedecin;
import ma.project.dentalTech.repository.modules.dossierMedical.api.InterventionMedecinRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private final Connection connection;

    public InterventionMedecinRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<InterventionMedecin> findAll() {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM intervention_medecin";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<InterventionMedecin> findById(Long id) {
        String sql = "SELECT * FROM intervention_medecin WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(InterventionMedecin intervention) {
        String sql = "INSERT INTO intervention_medecin (patient_id, utilisateur_id, date_intervention, type_intervention, description, prix_de_patient, nomb_delit) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, intervention.getPatientId());
            ps.setLong(2, intervention.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(intervention.getDateIntervention()));
            ps.setString(4, intervention.getTypeIntervention());
            ps.setString(5, intervention.getDescription());
            ps.setDouble(6, intervention.getPrixDePatient() != null ? intervention.getPrixDePatient() : 0.0);
            ps.setInt(7, intervention.getNombDelit() != null ? intervention.getNombDelit() : 0);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                intervention.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(InterventionMedecin intervention) {
        String sql = "UPDATE intervention_medecin SET patient_id=?, utilisateur_id=?, date_intervention=?, type_intervention=?, description=?, prix_de_patient=?, nomb_delit=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, intervention.getPatientId());
            ps.setLong(2, intervention.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(intervention.getDateIntervention()));
            ps.setString(4, intervention.getTypeIntervention());
            ps.setString(5, intervention.getDescription());
            ps.setDouble(6, intervention.getPrixDePatient() != null ? intervention.getPrixDePatient() : 0.0);
            ps.setInt(7, intervention.getNombDelit() != null ? intervention.getNombDelit() : 0);
            ps.setLong(8, intervention.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(InterventionMedecin intervention) {
        deleteById(intervention.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM intervention_medecin WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InterventionMedecin> findByPatientId(Long patientId) {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM intervention_medecin WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<InterventionMedecin> findByUtilisateurId(Long utilisateurId) {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM intervention_medecin WHERE utilisateur_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<InterventionMedecin> findByDateInterventionBetween(LocalDateTime debut, LocalDateTime fin) {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM intervention_medecin WHERE date_intervention BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToIntervention(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private InterventionMedecin mapToIntervention(ResultSet rs) throws SQLException {
        InterventionMedecin intervention = new InterventionMedecin();
        intervention.setId(rs.getLong("id"));
        intervention.setPatientId(rs.getLong("patient_id"));
        intervention.setUtilisateurId(rs.getLong("utilisateur_id"));

        Timestamp ts = rs.getTimestamp("date_intervention");
        intervention.setDateIntervention(ts != null ? ts.toLocalDateTime() : null);

        intervention.setTypeIntervention(rs.getString("type_intervention"));
        intervention.setDescription(rs.getString("description"));
        intervention.setPrixDePatient(rs.getDouble("prix_de_patient"));
        intervention.setNombDelit(rs.getInt("nomb_delit"));

        return intervention;
    }
}
