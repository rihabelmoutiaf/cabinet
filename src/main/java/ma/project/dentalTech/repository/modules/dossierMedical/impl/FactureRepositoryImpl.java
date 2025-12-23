package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Facture;
import ma.project.dentalTech.repository.modules.dossierMedical.api.FactureRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactureRepositoryImpl implements FactureRepository {

    private final Connection connection;

    public FactureRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Facture> findAll() {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM facture";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToFacture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Facture> findById(Long id) {
        String sql = "SELECT * FROM facture WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToFacture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(Facture facture) {
        String sql = "INSERT INTO facture (patient_id, montant_total, total_paye, est_payee, statut, date_facture, numero_facture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, facture.getPatientId());
            ps.setDouble(2, facture.getMontantTotal() != null ? facture.getMontantTotal() : 0.0);
            ps.setDouble(3, facture.getTotalPaye() != null ? facture.getTotalPaye() : 0.0);
            ps.setBoolean(4, facture.isEstPayee());
            ps.setString(5, facture.getStatut());
            ps.setTimestamp(6, Timestamp.valueOf(facture.getDateFacture()));
            ps.setString(7, facture.getNumeroFacture());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                facture.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Facture facture) {
        String sql = "UPDATE facture SET patient_id=?, montant_total=?, total_paye=?, est_payee=?, statut=?, date_facture=?, numero_facture=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, facture.getPatientId());
            ps.setDouble(2, facture.getMontantTotal() != null ? facture.getMontantTotal() : 0.0);
            ps.setDouble(3, facture.getTotalPaye() != null ? facture.getTotalPaye() : 0.0);
            ps.setBoolean(4, facture.isEstPayee());
            ps.setString(5, facture.getStatut());
            ps.setTimestamp(6, Timestamp.valueOf(facture.getDateFacture()));
            ps.setString(7, facture.getNumeroFacture());
            ps.setLong(8, facture.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Facture facture) {
        deleteById(facture.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM facture WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Facture> findByPatientId(Long patientId) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM facture WHERE patient_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToFacture(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Facture mapToFacture(ResultSet rs) throws SQLException {
        Facture facture = new Facture();
        facture.setId(rs.getLong("id"));
        facture.setPatientId(rs.getLong("patient_id"));
        facture.setMontantTotal(rs.getDouble("montant_total"));
        facture.setTotalPaye(rs.getDouble("total_paye"));
        facture.setEstPayee(rs.getBoolean("est_payee"));
        facture.setNumeroFacture(rs.getString("numero_facture"));

        Timestamp ts = rs.getTimestamp("date_facture");
        facture.setDateFacture(ts != null ? ts.toLocalDateTime() : LocalDateTime.now());

        // recalcul automatique de l’état
        facture.setTotalPaye(facture.getTotalPaye()); // déclenche calculEtat()
        return facture;
    }
}
