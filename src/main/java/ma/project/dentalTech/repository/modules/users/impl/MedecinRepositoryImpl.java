package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.repository.modules.users.api.MedecinRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinRepositoryImpl implements MedecinRepository {

    private Connection connection;

    public MedecinRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Medecin save(Medecin medecin) {
        String sql = """
            INSERT INTO medecin
            (nom, prenom, email, adresse, telephone, date_creation, specialite, agenda_mensuel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, medecin.getNom());
            ps.setString(2, medecin.getPrenom());
            ps.setString(3, medecin.getEmail());
            ps.setString(4, medecin.getAdresse());
            ps.setString(5, medecin.getTelephone());
            ps.setTimestamp(6, medecin.getDateCreation() != null ? Timestamp.valueOf(medecin.getDateCreation()) : null);
            ps.setString(7, medecin.getSpecialite());
            ps.setString(8, medecin.getAgendaMensuel());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                medecin.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medecin;
    }

    @Override
    public Medecin findById(Long id) {
        String sql = "SELECT * FROM medecin WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToMedecin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Medecin findByEmail(String email) {
        String sql = "SELECT * FROM medecin WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToMedecin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Medecin> findBySpecialite(String specialite) {
        List<Medecin> list = new ArrayList<>();
        String sql = "SELECT * FROM medecin WHERE specialite = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, specialite);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToMedecin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Medecin> findAll() {
        List<Medecin> list = new ArrayList<>();
        String sql = "SELECT * FROM medecin ORDER BY nom";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToMedecin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Medecin medecin) {
        String sql = """
            UPDATE medecin SET
            nom = ?, prenom = ?, email = ?, adresse = ?, telephone = ?, date_creation = ?, specialite = ?, agenda_mensuel = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, medecin.getNom());
            ps.setString(2, medecin.getPrenom());
            ps.setString(3, medecin.getEmail());
            ps.setString(4, medecin.getAdresse());
            ps.setString(5, medecin.getTelephone());
            ps.setTimestamp(6, medecin.getDateCreation() != null ? Timestamp.valueOf(medecin.getDateCreation()) : null);
            ps.setString(7, medecin.getSpecialite());
            ps.setString(8, medecin.getAgendaMensuel());
            ps.setLong(9, medecin.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM medecin WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Medecin mapToMedecin(ResultSet rs) throws SQLException {
        Medecin med = new Medecin();
        med.setId(rs.getLong("id"));
        med.setNom(rs.getString("nom"));
        med.setPrenom(rs.getString("prenom"));
        med.setEmail(rs.getString("email"));
        med.setAdresse(rs.getString("adresse"));
        med.setTelephone(rs.getString("telephone"));
        Timestamp ts = rs.getTimestamp("date_creation");
        if (ts != null) med.setDateCreation(ts.toLocalDateTime());
        med.setSpecialite(rs.getString("specialite"));
        med.setAgendaMensuel(rs.getString("agenda_mensuel"));
        return med;
    }
}
