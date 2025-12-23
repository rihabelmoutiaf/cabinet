package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.enums.Sexe;
import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.repository.modules.users.api.AdminRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {

    private Connection connection;

    public AdminRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Admin save(Admin admin) {
        String sql = """
            INSERT INTO admin
            (nom, prenom, email, adresse, tel, sexe, login, mot_de_passe, role_id, active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getAdresse());
            ps.setString(5, admin.getTel());
            ps.setString(6, admin.getSexe() != null ? admin.getSexe().name() : null);
            ps.setString(7, admin.getLogin());
            ps.setString(8, admin.getMotDePasse());
            ps.setObject(9, admin.getRoleId());
            ps.setBoolean(10, admin.isActive());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                admin.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

    @Override
    public Admin findById(Long id) {
        String sql = "SELECT * FROM admin WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToAdmin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin findByEmail(String email) {
        String sql = "SELECT * FROM admin WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToAdmin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin findByLogin(String login) {
        String sql = "SELECT * FROM admin WHERE login = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToAdmin(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admin ORDER BY nom";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToAdmin(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Admin admin) {
        String sql = """
            UPDATE admin SET
            nom = ?, prenom = ?, email = ?, adresse = ?, tel = ?, sexe = ?,
            login = ?, mot_de_passe = ?, role_id = ?, active = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getPrenom());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getAdresse());
            ps.setString(5, admin.getTel());
            ps.setString(6, admin.getSexe() != null ? admin.getSexe().name() : null);
            ps.setString(7, admin.getLogin());
            ps.setString(8, admin.getMotDePasse());
            ps.setObject(9, admin.getRoleId());
            ps.setBoolean(10, admin.isActive());
            ps.setLong(11, admin.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM admin WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Admin mapToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getLong("id"));
        admin.setNom(rs.getString("nom"));
        admin.setPrenom(rs.getString("prenom"));
        admin.setEmail(rs.getString("email"));
        admin.setAdresse(rs.getString("adresse"));
        admin.setTel(rs.getString("tel"));
        String sexeStr = rs.getString("sexe");
        if (sexeStr != null) {
            admin.setSexe(Sexe.valueOf(sexeStr));
        }
        admin.setLogin(rs.getString("login"));
        admin.setMotDePasse(rs.getString("mot_de_passe"));
        admin.setRoleId(rs.getObject("role_id") != null ? rs.getLong("role_id") : null);
        admin.setActive(rs.getBoolean("active"));
        return admin;
    }
}
