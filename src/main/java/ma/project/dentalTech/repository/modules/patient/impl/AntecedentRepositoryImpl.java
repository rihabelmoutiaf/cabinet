package ma.project.dentalTech.repository.modules.patient.impl;

import ma.project.dentalTech.entities.patient.Antecedent;
import ma.project.dentalTech.entities.enums.CategorieAntecedent;
import ma.project.dentalTech.entities.enums.NiveauRisque;
import ma.project.dentalTech.repository.modules.patient.api.AntecedentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    private Connection connection;

    public AntecedentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Antecedent save(Antecedent antecedent) {
        String sql = """
            INSERT INTO antecedent
            (patient_id, nom, categorie, niveau_risque)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, antecedent.getPatientId());
            ps.setString(2, antecedent.getNom());
            ps.setString(3, antecedent.getCategorie().name());
            ps.setString(4, antecedent.getNiveauRisque().name());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                antecedent.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antecedent;
    }

    @Override
    public Antecedent findById(Long id) {
        String sql = "SELECT * FROM antecedent WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToAntecedent(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Antecedent> findByPatient(Long patientId) {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM antecedent WHERE patient_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToAntecedent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Antecedent> findByCategorie(String categorie) {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM antecedent WHERE categorie = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categorie);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToAntecedent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Antecedent> findAll() {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM antecedent ORDER BY patient_id";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToAntecedent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Antecedent antecedent) {
        String sql = """
            UPDATE antecedent SET
            nom = ?, categorie = ?, niveau_risque = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, antecedent.getNom());
            ps.setString(2, antecedent.getCategorie().name());
            ps.setString(3, antecedent.getNiveauRisque().name());
            ps.setLong(4, antecedent.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM antecedent WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Antecedent mapToAntecedent(ResultSet rs) throws SQLException {
        Antecedent antecedent = new Antecedent();
        antecedent.setId(rs.getLong("id"));
        antecedent.setPatientId(rs.getLong("patient_id"));
        antecedent.setNom(rs.getString("nom"));
        antecedent.setCategorie(CategorieAntecedent.valueOf(rs.getString("categorie")));
        antecedent.setNiveauRisque(NiveauRisque.valueOf(rs.getString("niveau_risque")));
        return antecedent;
    }
}
