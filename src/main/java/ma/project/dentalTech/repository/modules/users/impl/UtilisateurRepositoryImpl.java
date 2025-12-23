package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.configuration.SessionFactory;
import ma.project.dentalTech.repository.common.RowMappers;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    @Override
    public Utilisateur save(Utilisateur u) {
        String sql = """
            INSERT INTO utilisateurs(
                type_user, nom, prenom, email, tel, adresse, sexe, 
                date_naissance, login, mot_de_passe, actif, 
                premier_connexion, role_id
            ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, determineTypeUser(u));
            ps.setString(2, u.getNom());
            ps.setString(3, u.getPrenom());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getTel());
            ps.setString(6, u.getAdresse());
            ps.setString(7, u.getSexe() != null ? u.getSexe().name() : null);

            if (u.getDateNaissance() != null) {
                ps.setDate(8, Date.valueOf(u.getDateNaissance()));
            } else {
                ps.setNull(8, Types.DATE);
            }

            ps.setString(9, u.getLogin());
            ps.setString(10, u.getMotDePasse());
            ps.setBoolean(11, u.isActive());
            ps.setBoolean(12, true); // premier_connexion
            ps.setLong(13, u.getRoleId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    u.setId(keys.getLong(1));
                }
            }

            return u;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @Override
    public Utilisateur update(Utilisateur u) {
        String sql = """
            UPDATE utilisateurs SET 
                nom=?, prenom=?, email=?, tel=?, adresse=?, sexe=?,
                date_naissance=?, login=?, actif=?, role_id=?, updated_at=CURRENT_TIMESTAMP
            WHERE id=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTel());
            ps.setString(5, u.getAdresse());
            ps.setString(6, u.getSexe() != null ? u.getSexe().name() : null);

            if (u.getDateNaissance() != null) {
                ps.setDate(7, Date.valueOf(u.getDateNaissance()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setString(8, u.getLogin());
            ps.setBoolean(9, u.isActive());
            ps.setLong(10, u.getRoleId());
            ps.setLong(11, u.getId());

            ps.executeUpdate();
            return u;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM utilisateurs WHERE id=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    @Override
    public Utilisateur findById(Long id) {
        String sql = "SELECT * FROM utilisateurs WHERE id=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapUtilisateur(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur", e);
        }
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM utilisateurs ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                utilisateurs.add(RowMappers.mapUtilisateur(rs));
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs", e);
        }
    }

    @Override
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateurs WHERE email=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapUtilisateur(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par email", e);
        }
    }

    @Override
    public Utilisateur findByLogin(String login) {
        String sql = "SELECT * FROM utilisateurs WHERE login=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return RowMappers.mapUtilisateur(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par login", e);
        }
    }

    @Override
    public List<Utilisateur> findByTypeUser(String typeUser) {
        String sql = "SELECT * FROM utilisateurs WHERE type_user=? ORDER BY nom, prenom";
        List<Utilisateur> utilisateurs = new ArrayList<>();

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, typeUser);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    utilisateurs.add(RowMappers.mapUtilisateur(rs));
                }
            }

            return utilisateurs;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche par type", e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM utilisateurs WHERE email=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification de l'email", e);
        }
    }

    @Override
    public boolean existsByLogin(String login) {
        String sql = "SELECT 1 FROM utilisateurs WHERE login=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du login", e);
        }
    }

    @Override
    public void updateLastLogin(Long userId) {
        String sql = """
            UPDATE utilisateurs 
            SET date_derniere_connexion=?, premier_connexion=FALSE, updated_at=CURRENT_TIMESTAMP
            WHERE id=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setLong(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la dernière connexion", e);
        }
    }

    @Override
    public void incrementFailedAttempts(Long userId) {
        String sql = "UPDATE utilisateurs SET tentatives_echouees = tentatives_echouees + 1 WHERE id=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'incrémentation des tentatives échouées", e);
        }
    }

    @Override
    public void resetFailedAttempts(Long userId) {
        String sql = "UPDATE utilisateurs SET tentatives_echouees=0, date_verrouillage=NULL WHERE id=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la réinitialisation des tentatives", e);
        }
    }

    @Override
    public void lockAccount(Long userId) {
        String sql = "UPDATE utilisateurs SET actif=FALSE, date_verrouillage=CURRENT_TIMESTAMP WHERE id=?";

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du verrouillage du compte", e);
        }
    }

    @Override
    public void unlockAccount(Long userId) {
        String sql = """
            UPDATE utilisateurs 
            SET actif=TRUE, tentatives_echouees=0, date_verrouillage=NULL 
            WHERE id=?
            """;

        try (Connection c = SessionFactory.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du déverrouillage du compte", e);
        }
    }

    // Méthode helper pour déterminer le type d'utilisateur
    private String determineTypeUser(Utilisateur u) {
        String className = u.getClass().getSimpleName();

        return switch (className) {
            case "Admin" -> "ADMIN";
            case "Medecin" -> "MEDECIN";
            case "Secretaire" -> "SECRETAIRE";
            case "Staff" -> "STAFF";
            default -> "STAFF";
        };
    }
}