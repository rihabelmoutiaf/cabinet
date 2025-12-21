package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.repository.modules.users.api.AdminRepository;
import ma.project.dentalTech.configuration.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminRepositoryImpl implements AdminRepository {

    private final Connection connection;

    public AdminRepositoryImpl() {
        this.connection = ConnectionPool.getConnection();
    }

    @Override
    public Admin save(Admin admin) {
        String sql = """
            INSERT INTO utilisateurs (nom, email, mot_de_passe, role_id, actif, type_user)
            VALUES (?, ?, ?, ?, ?, 'ADMIN')
        """;

        try (PreparedStatement ps =
                     connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getMotDePasse());
            ps.setLong(4, admin.getRole().getId());
            ps.setBoolean(5, admin.isActif());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                admin.setId(rs.getLong(1));
            }

            return admin;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion Admin", e);
        }
    }

    @Override
    public Admin findById(Long id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ? AND type_user = 'ADMIN'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setNom(rs.getString("nom"));
                admin.setEmail(rs.getString("email"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));
                admin.setActif(rs.getBoolean("actif"));
                return admin;
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Admin", e);
        }
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs WHERE type_user = 'ADMIN'";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setNom(rs.getString("nom"));
                admin.setEmail(rs.getString("email"));
                admin.setMotDePasse(rs.getString("mot_de_passe"));
                admin.setActif(rs.getBoolean("actif"));
                admins.add(admin);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Admin", e);
        }

        return admins;
    }

    @Override
    public Admin update(Admin admin) {
        String sql = """
            UPDATE utilisateurs
            SET nom = ?, email = ?, mot_de_passe = ?, actif = ?
            WHERE id = ? AND type_user = 'ADMIN'
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, admin.getNom());
            ps.setString(2, admin.getEmail());
            ps.setString(3, admin.getMotDePasse());
            ps.setBoolean(4, admin.isActif());
            ps.setLong(5, admin.getId());

            ps.executeUpdate();
            return admin;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Admin", e);
        }
    }

    @Override
    public void delete(Admin admin) {
        String sql = "DELETE FROM utilisateurs WHERE id = ? AND type_user = 'ADMIN'";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, admin.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur delete Admin", e);
        }
    }
}
