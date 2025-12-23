package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.configuration.SessionFactory;
import ma.project.dentalTech.entities.enums.RoleType;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.repository.common.RowMappers;
import ma.project.dentalTech.repository.modules.users.api.RoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {

    @Override
    public Role save(Role role) {
        String sql = "INSERT INTO roles(nom, description) VALUES(?, ?)";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, role.getNom());
            ps.setString(2, role.getDescription());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    role.setId(keys.getLong(1));
                }
            }

            return role;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création du rôle", e);
        }
    }

    @Override
    public Role update(Role role) {
        String sql = "UPDATE roles SET nom = ?, description = ? WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, role.getNom());
            ps.setString(2, role.getDescription());
            ps.setLong(3, role.getId());

            ps.executeUpdate();
            return role;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du rôle", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM roles WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du rôle", e);
        }
    }

    @Override
    public Role findById(Long id) {
        String sql = "SELECT * FROM roles WHERE id = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRole(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du rôle", e);
        }
    }

    @Override
    public List<Role> findAll() {
        String sql = "SELECT * FROM roles ORDER BY nom";
        List<Role> roles = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(RowMappers.mapRole(rs));
            }

            return roles;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des rôles", e);
        }
    }

    @Override
    public Role findByRoleType(RoleType roleType) {
        String sql = "SELECT * FROM roles WHERE nom = ?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, roleType.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapRole(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du rôle par type", e);
        }
    }
}