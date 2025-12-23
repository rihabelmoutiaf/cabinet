package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.enums.Sexe;
import ma.project.dentalTech.entities.users.Secretaire;
import ma.project.dentalTech.repository.modules.users.api.SecretaireRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecretaireRepositoryImpl implements SecretaireRepository {

    private Connection connection;

    public SecretaireRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Secretaire save(Secretaire s) {
        String sql = """
            INSERT INTO secretaire
            (nom, prenom, email, adresse, telephone, date_creation, sexe, num_cnss, commission)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getAdresse());
            ps.setString(5, s.getTelephone());
            ps.setTimestamp(6, s.getDateCreation() != null ? Timestamp.valueOf(s.getDateCreation()) : null);
            ps.setString(7, s.getSexe() != null ? s.getSexe().name() : null);
            ps.setString(8, s.getNumCNSS());
            ps.setObject(9, s.getCommission());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                s.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    @Override
    public Secretaire findById(Long id) {
        String sql = "SELECT * FROM secretaire WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToSecretaire(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Secretaire findByEmail(String email) {
        String sql = "SELECT * FROM secretaire WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToSecretaire(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Secretaire> findAll() {
        List<Secretaire> list = new ArrayList<>();
        String sql = "SELECT * FROM secretaire ORDER BY nom";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToSecretaire(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Secretaire s) {
        String sql = """
            UPDATE secretaire SET
            nom = ?, prenom = ?, email = ?, adresse = ?, telephone = ?, date_creation = ?, sexe = ?, num_cnss = ?, commission = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getNom());
            ps.setString(2, s.getPrenom());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getAdresse());
            ps.setString(5, s.getTelephone());
            ps.setTimestamp(6, s.getDateCreation() != null ? Timestamp.valueOf(s.getDateCreation()) : null);
            ps.setString(7, s.getSexe() != null ? s.getSexe().name() : null);
            ps.setString(8, s.getNumCNSS());
            ps.setObject(9, s.getCommission());
            ps.setLong(10, s.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM secretaire WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Secretaire mapToSecretaire(ResultSet rs) throws SQLException {
        Secretaire s = new Secretaire();
        s.setId(rs.getLong("id"));
        s.setNom(rs.getString("nom"));
        s.setPrenom(rs.getString("prenom"));
        s.setEmail(rs.getString("email"));
        s.setAdresse(rs.getString("adresse"));
        s.setTelephone(rs.getString("telephone"));
        Timestamp ts = rs.getTimestamp("date_creation");
        if (ts != null) s.setDateCreation(ts.toLocalDateTime());
        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) s.setSexe(Sexe.valueOf(sexeStr));
        s.setNumCNSS(rs.getString("num_cnss"));
        s.setCommission(rs.getObject("commission") != null ? rs.getDouble("commission") : null);
        return s;
    }
}
