package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.enums.Sexe;
import ma.project.dentalTech.entities.users.Role;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class UtilisateurRepositoryImpl<T extends Utilisateur> implements UtilisateurRepository<T> {

    protected Connection connection;
    protected String tableName;

    public UtilisateurRepositoryImpl(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    @Override
    public T save(T u) {
        String sql = String.format("""
            INSERT INTO %s
            (nom, prenom, email, adresse, tel, sexe, login, mot_de_passe, date_derniere_connexion, date_naissance, role_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, tableName);

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAdresse());
            ps.setString(5, u.getTel());
            ps.setString(6, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setString(7, u.getLogin());
            ps.setString(8, u.getMotDePasse());
            ps.setDate(9, u.getDateDerniereConnexion() != null ? Date.valueOf(u.getDateDerniereConnexion()) : null);
            ps.setDate(10, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            ps.setObject(11, u.getRoleId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    @Override
    public T findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T findByEmail(String email) {
        String sql = String.format("SELECT * FROM %s WHERE email = ?", tableName);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToUtilisateur(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY nom", tableName);
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> findByRoleId(Long roleId) {
        List<T> list = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s WHERE role_id = ?", tableName);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, roleId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToUtilisateur(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(T u) {
        String sql = String.format("""
            UPDATE %s SET
            nom = ?, prenom = ?, email = ?, adresse = ?, tel = ?, sexe = ?, login = ?, mot_de_passe = ?, date_derniere_connexion = ?, date_naissance = ?, role_id = ?
            WHERE id = ?
            """, tableName);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getAdresse());
            ps.setString(5, u.getTel());
            ps.setString(6, u.getSexe() != null ? u.getSexe().name() : null);
            ps.setString(7, u.getLogin());
            ps.setString(8, u.getMotDePasse());
            ps.setDate(9, u.getDateDerniereConnexion() != null ? Date.valueOf(u.getDateDerniereConnexion()) : null);
            ps.setDate(10, u.getDateNaissance() != null ? Date.valueOf(u.getDateNaissance()) : null);
            ps.setObject(11, u.getRoleId());
            ps.setLong(12, u.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", tableName);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract T mapToUtilisateur(ResultSet rs) throws SQLException;
}
