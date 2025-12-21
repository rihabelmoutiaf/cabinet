package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.common.BaseRepositoryImpl;
import ma.project.dentalTech.repository.common.RowMapper;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurRepositoryImpl
        extends BaseRepositoryImpl<Utilisateur>
        implements UtilisateurRepository {

    @Override
    protected String getTableName() {
        return "utilisateurs";
    }

    @Override
    protected RowMapper<Utilisateur> getRowMapper() {
        return rs -> {
            Utilisateur u = new Utilisateur() {};
            u.setId(rs.getLong("id"));
            u.setNom(rs.getString("nom"));
            u.setEmail(rs.getString("email"));
            u.setMotDePasse(rs.getString("mot_de_passe"));
            u.setActif(rs.getBoolean("actif"));
            return u;
        };
    }

    @Override
    public Utilisateur save(Utilisateur u) {
        String sql = "INSERT INTO utilisateurs(nom,email,mot_de_passe,actif) VALUES (?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getMotDePasse());
            ps.setBoolean(4, u.isActif());
            ps.executeUpdate();
            return u;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Utilisateur update(Utilisateur u) {
        String sql = "UPDATE utilisateurs SET nom=?, email=?, actif=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setBoolean(3, u.isActif());
            ps.setLong(4, u.getId());
            ps.executeUpdate();
            return u;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM utilisateurs WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateurs WHERE email=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getRowMapper().mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
