package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Acte;
import ma.project.dentalTech.repository.modules.dossierMedical.api.ActeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActeRepositoryImpl implements ActeRepository {

    private Connection connection;

    public ActeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Acte save(Acte acte) {
        String sql = "INSERT INTO acte (nom, description, type_acte, tarif_base) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, acte.getNom());
            ps.setString(2, acte.getDescription());
            ps.setString(3, acte.getTypeActe());
            ps.setObject(4, acte.getTarifBase());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                acte.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acte;
    }

    @Override
    public Acte findById(Long id) {
        String sql = "SELECT * FROM acte WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToActe(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Acte findByNom(String nom) {
        String sql = "SELECT * FROM acte WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToActe(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Acte> findAll() {
        List<Acte> list = new ArrayList<>();
        String sql = "SELECT * FROM acte ORDER BY nom";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToActe(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Acte acte) {
        String sql = "UPDATE acte SET nom = ?, description = ?, type_acte = ?, tarif_base = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, acte.getNom());
            ps.setString(2, acte.getDescription());
            ps.setString(3, acte.getTypeActe());
            ps.setObject(4, acte.getTarifBase());
            ps.setLong(5, acte.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM acte WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================== Méthodes spécifiques ==================

    @Override
    public List<Acte> findByConsultationId(Long consultationId) {
        List<Acte> list = new ArrayList<>();
        String sql = """
            SELECT a.* FROM acte a
            JOIN consultation_acte ca ON a.id = ca.acte_id
            WHERE ca.consultation_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, consultationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToActe(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Acte> findByTypeActe(String typeActe) {
        List<Acte> list = new ArrayList<>();
        String sql = "SELECT * FROM acte WHERE type_acte = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, typeActe);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToActe(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double sumTarifByConsultation(Long consultationId) {
        String sql = """
            SELECT SUM(a.tarif_base) AS total
            FROM acte a
            JOIN consultation_acte ca ON a.id = ca.acte_id
            WHERE ca.consultation_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, consultationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // ================== Mapping ==================

    private Acte mapToActe(ResultSet rs) throws SQLException {
        Acte acte = new Acte();
        acte.setId(rs.getLong("id"));
        acte.setNom(rs.getString("nom"));
        acte.setDescription(rs.getString("description"));
        acte.setTypeActe(rs.getString("type_acte"));
        acte.setTarifBase(rs.getObject("tarif_base") != null ? rs.getDouble("tarif_base") : null);
        return acte;
    }
}
