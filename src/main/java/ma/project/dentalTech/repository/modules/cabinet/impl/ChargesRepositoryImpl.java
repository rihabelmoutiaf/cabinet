package ma.project.dentalTech.repository.modules.cabinet.impl;

import ma.project.dentalTech.entities.cabinet.Charges;
import ma.project.dentalTech.repository.modules.cabinet.api.ChargesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChargesRepositoryImpl implements ChargesRepository {

    private Connection connection;

    public ChargesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Charges save(Charges charge) {
        String sql = """
            INSERT INTO charges
            (titre, description, montant, date, utilisateur_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, charge.getTitre());
            ps.setString(2, charge.getDescription());
            ps.setDouble(3, charge.getMontant());
            ps.setTimestamp(4, Timestamp.valueOf(charge.getDate()));
            ps.setLong(5, charge.getUtilisateurId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                charge.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return charge;
    }

    @Override
    public Charges findById(Long id) {
        String sql = "SELECT * FROM charges WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToCharges(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Charges> findAll() {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM charges ORDER BY date DESC";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToCharges(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Charges> findByUtilisateur(Long utilisateurId) {
        List<Charges> list = new ArrayList<>();
        String sql = "SELECT * FROM charges WHERE utilisateur_id = ? ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, utilisateurId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToCharges(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Charges charge) {
        String sql = """
            UPDATE charges SET
            titre = ?, description = ?, montant = ?, date = ?, utilisateur_id = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, charge.getTitre());
            ps.setString(2, charge.getDescription());
            ps.setDouble(3, charge.getMontant());
            ps.setTimestamp(4, Timestamp.valueOf(charge.getDate()));
            ps.setLong(5, charge.getUtilisateurId());
            ps.setLong(6, charge.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM charges WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Charges mapToCharges(ResultSet rs) throws SQLException {
        Charges charge = new Charges();
        charge.setId(rs.getLong("id"));
        charge.setTitre(rs.getString("titre"));
        charge.setDescription(rs.getString("description"));
        charge.setMontant(rs.getDouble("montant"));
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) {
            charge.setDate(ts.toLocalDateTime());
        }
        charge.setUtilisateurId(rs.getLong("utilisateur_id"));
        return charge;
    }
}
