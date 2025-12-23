package ma.project.dentalTech.repository.modules.dossierMedical.impl;

import ma.project.dentalTech.entities.dossierMedical.Medicament;
import ma.project.dentalTech.repository.modules.dossierMedical.api.MedicamentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicamentRepositoryImpl implements MedicamentRepository {

    private final Connection connection;

    public MedicamentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Medicament> findAll() {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM medicament";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapToMedicament(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Medicament> findById(Long id) {
        String sql = "SELECT * FROM medicament WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToMedicament(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void create(Medicament medicament) {
        String sql = "INSERT INTO medicament (nom, laboratoire, dosage, presentation, type_dosage, forme, remboursable, posologie, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLaboratoire());
            ps.setString(3, medicament.getDosage());
            ps.setString(4, medicament.getPresentation());
            ps.setString(5, medicament.getTypeDosage());
            ps.setString(6, medicament.getForme());
            ps.setBoolean(7, medicament.isRemboursable());
            ps.setString(8, medicament.getPosologie());
            ps.setString(9, medicament.getDescription());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                medicament.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Medicament medicament) {
        String sql = "UPDATE medicament SET nom=?, laboratoire=?, dosage=?, presentation=?, type_dosage=?, forme=?, remboursable=?, posologie=?, description=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, medicament.getNom());
            ps.setString(2, medicament.getLaboratoire());
            ps.setString(3, medicament.getDosage());
            ps.setString(4, medicament.getPresentation());
            ps.setString(5, medicament.getTypeDosage());
            ps.setString(6, medicament.getForme());
            ps.setBoolean(7, medicament.isRemboursable());
            ps.setString(8, medicament.getPosologie());
            ps.setString(9, medicament.getDescription());
            ps.setLong(10, medicament.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Medicament medicament) {
        deleteById(medicament.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM medicament WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medicament> findByNom(String nom) {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM medicament WHERE nom LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToMedicament(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Medicament> findByLaboratoire(String laboratoire) {
        List<Medicament> list = new ArrayList<>();
        String sql = "SELECT * FROM medicament WHERE laboratoire LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + laboratoire + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapToMedicament(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Medicament mapToMedicament(ResultSet rs) throws SQLException {
        Medicament m = new Medicament();
        m.setId(rs.getLong("id"));
        m.setNom(rs.getString("nom"));
        m.setLaboratoire(rs.getString("laboratoire"));
        m.setDosage(rs.getString("dosage"));
        m.setPresentation(rs.getString("presentation"));
        m.setTypeDosage(rs.getString("type_dosage"));
        m.setForme(rs.getString("forme"));
        m.setRemboursable(rs.getBoolean("remboursable"));
        m.setPosologie(rs.getString("posologie"));
        m.setDescription(rs.getString("description"));
        return m;
    }
}
