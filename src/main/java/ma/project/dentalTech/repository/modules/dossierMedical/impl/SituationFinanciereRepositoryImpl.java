package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.SituationFinanciere;
import ma.project.dentalTech.entities.enums.StatutSituationFinanciere;
import ma.project.dentalTech.repository.modules.dossierMedical.api.SituationFinanciereRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    private final Connection connection;

    public SituationFinanciereRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<SituationFinanciere> findAll() {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM situation_financiere";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToSituation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<SituationFinanciere> findById(Long id) {
        String sql = "SELECT * FROM situation_financiere WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToSituation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(SituationFinanciere situation) {
        String sql = "INSERT INTO situation_financiere (patient_id, total_des_actes, totale_paye, credit, statut, observation) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, situation.getPatientId());
            ps.setDouble(2, situation.getTotalDesActes() != null ? situation.getTotalDesActes() : 0.0);
            ps.setDouble(3, situation.getTotalePaye() != null ? situation.getTotalePaye() : 0.0);
            ps.setDouble(4, situation.getCredit() != null ? situation.getCredit() : 0.0);
            ps.setString(5, situation.getStatut() != null ? situation.getStatut().name() : null);
            ps.setString(6, situation.getObservation());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                situation.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SituationFinanciere situation) {
        String sql = "UPDATE situation_financiere SET patient_id=?, total_des_actes=?, totale_paye=?, credit=?, statut=?, observation=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, situation.getPatientId());
            ps.setDouble(2, situation.getTotalDesActes() != null ? situation.getTotalDesActes() : 0.0);
            ps.setDouble(3, situation.getTotalePaye() != null ? situation.getTotalePaye() : 0.0);
            ps.setDouble(4, situation.getCredit() != null ? situation.getCredit() : 0.0);
            ps.setString(5, situation.getStatut() != null ? situation.getStatut().name() : null);
            ps.setString(6, situation.getObservation());
            ps.setLong(7, situation.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(SituationFinanciere situation) {
        deleteById(situation.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM situation_financiere WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SituationFinanciere> findByPatientId(Long patientId) {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM situation_financiere WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToSituation(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private SituationFinanciere mapToSituation(ResultSet rs) throws SQLException {
        SituationFinanciere s = new SituationFinanciere();
        s.setId(rs.getLong("id"));
        s.setPatientId(rs.getLong("patient_id"));
        s.setTotalDesActes(rs.getDouble("total_des_actes"));
        s.setTotalePaye(rs.getDouble("totale_paye"));
        s.setCredit(rs.getDouble("credit"));

        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            s.setStatut(StatutSituationFinanciere.valueOf(statutStr));
        }

        s.setObservation(rs.getString("observation"));
        return s;
    }
}
