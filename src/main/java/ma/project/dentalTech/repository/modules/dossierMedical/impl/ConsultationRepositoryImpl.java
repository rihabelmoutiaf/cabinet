package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.entities.enums.StatutRDV;
import ma.project.dentalTech.repository.modules.dossierMedical.api.ConsultationRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private final Connection connection;

    public ConsultationRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Consultation> findAll() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM consultation";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Consultation> findById(Long id) {
        String sql = "SELECT * FROM consultation WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(Consultation consultation) {
        String sql = "INSERT INTO consultation (patient_id, utilisateur_id, date_consultation, diagnostic, traitement, statut, observation_medecin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, consultation.getPatientId());
            ps.setLong(2, consultation.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(consultation.getDateConsultation()));
            ps.setString(4, consultation.getDiagnostic());
            ps.setString(5, consultation.getTraitement());
            ps.setString(6, consultation.getStatut() != null ? consultation.getStatut().name() : null);
            ps.setString(7, consultation.getObservationMedecin());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                consultation.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Consultation consultation) {
        String sql = "UPDATE consultation SET patient_id=?, utilisateur_id=?, date_consultation=?, diagnostic=?, traitement=?, statut=?, observation_medecin=? " +
                "WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, consultation.getPatientId());
            ps.setLong(2, consultation.getUtilisateurId());
            ps.setTimestamp(3, Timestamp.valueOf(consultation.getDateConsultation()));
            ps.setString(4, consultation.getDiagnostic());
            ps.setString(5, consultation.getTraitement());
            ps.setString(6, consultation.getStatut() != null ? consultation.getStatut().name() : null);
            ps.setString(7, consultation.getObservationMedecin());
            ps.setLong(8, consultation.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Consultation consultation) {
        deleteById(consultation.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM consultation WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Consultation> findByPatientId(Long patientId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM consultation WHERE patient_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public List<Consultation> findByUtilisateurId(Long utilisateurId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM consultation WHERE utilisateur_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public List<Consultation> findByMedecinName(String nomMedecin) {
        List<Consultation> list = new ArrayList<>();
        String sql = """
            SELECT c.* 
            FROM consultation c
            JOIN utilisateur u ON c.utilisateur_id = u.id
            WHERE u.nom LIKE ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nomMedecin + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToConsultation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Consultation> findByDateConsultationBetween(LocalDateTime debut, LocalDateTime fin) {
        return List.of();
    }

    private Consultation mapToConsultation(ResultSet rs) throws SQLException {
        Consultation c = new Consultation();
        c.setId(rs.getLong("id"));
        c.setPatientId(rs.getLong("patient_id"));
        c.setUtilisateurId(rs.getLong("utilisateur_id"));
        Timestamp ts = rs.getTimestamp("date_consultation");
        c.setDateConsultation(ts != null ? ts.toLocalDateTime() : null);
        c.setDiagnostic(rs.getString("diagnostic"));
        c.setTraitement(rs.getString("traitement"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            c.setStatut(StatutRDV.valueOf(statutStr));
        }

        c.setObservationMedecin(rs.getString("observation_medecin"));
        return c;
    }
}
