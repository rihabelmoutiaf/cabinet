package ma.project.dentalTech.repository.modules.cabinet.impl;

import ma.project.dentalTech.entities.cabinet.Revenues;
import ma.project.dentalTech.repository.modules.cabinet.api.RevenuesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevenuesRepositoryImpl implements RevenuesRepository {

    private Connection connection;

    public RevenuesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Revenues save(Revenues revenue) {
        String sql = """
            INSERT INTO revenues
            (facture_id, utilisateur_id, titre, description, montant, date)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setObject(1, revenue.getFactureId());
            ps.setObject(2, revenue.getUtilisateurId());
            ps.setString(3, revenue.getTitre());
            ps.setString(4, revenue.getDescription());
            ps.setDouble(5, revenue.getMontant());
            ps.setTimestamp(6, Timestamp.valueOf(revenue.getDate()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                revenue.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revenue;
    }

    @Override
    public Revenues findById(Long id) {
        String sql = "SELECT * FROM revenues WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToRevenue(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Revenues> findAll() {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM revenues ORDER BY date DESC";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToRevenue(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Revenues> findByUtilisateur(Long utilisateurId) {
        List<Revenues> list = new ArrayList<>();
        String sql = "SELECT * FROM revenues WHERE utilisateur_id = ? ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToRevenue(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Revenues revenue) {
        String sql = """
            UPDATE revenues SET
            facture_id = ?, utilisateur_id = ?, titre = ?, description = ?, montant = ?, date = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setObject(1, revenue.getFactureId());
            ps.setObject(2, revenue.getUtilisateurId());
            ps.setString(3, revenue.getTitre());
            ps.setString(4, revenue.getDescription());
            ps.setDouble(5, revenue.getMontant());
            ps.setTimestamp(6, Timestamp.valueOf(revenue.getDate()));
            ps.setLong(7, revenue.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM revenues WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Revenues mapToRevenue(ResultSet rs) throws SQLException {
        Revenues revenue = new Revenues();
        revenue.setId(rs.getLong("id"));
        revenue.setFactureId(rs.getObject("facture_id") != null ? rs.getLong("facture_id") : null);
        revenue.setUtilisateurId(rs.getObject("utilisateur_id") != null ? rs.getLong("utilisateur_id") : null);
        revenue.setTitre(rs.getString("titre"));
        revenue.setDescription(rs.getString("description"));
        revenue.setMontant(rs.getDouble("montant"));
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) revenue.setDate(ts.toLocalDateTime());
        return revenue;
    }
}
