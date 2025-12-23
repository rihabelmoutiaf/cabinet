package ma.project.dentalTech.repository.modules.cabinet.impl;

import ma.project.dentalTech.entities.cabinet.CabinetMedical;
import ma.project.dentalTech.repository.modules.cabinet.api.CabinetMedicalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabinetMedicalRepositoryImpl implements CabinetMedicalRepository {

    private Connection connection;

    public CabinetMedicalRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CabinetMedical save(CabinetMedical cabinet) {
        String sql = """
            INSERT INTO cabinet_medical
            (nom, email, logo, adresse, tel1, tel2, site_web, instagram, facebook, description)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cabinet.getNom());
            ps.setString(2, cabinet.getEmail());
            ps.setString(3, cabinet.getLogo());
            ps.setString(4, cabinet.getAdresse());
            ps.setString(5, cabinet.getTel1());
            ps.setString(6, cabinet.getTel2());
            ps.setString(7, cabinet.getSiteWeb());
            ps.setString(8, cabinet.getInstagram());
            ps.setString(9, cabinet.getFacebook());
            ps.setString(10, cabinet.getDescription());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cabinet.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabinet;
    }

    @Override
    public CabinetMedical findById(Long id) {
        String sql = "SELECT * FROM cabinet_medical WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToCabinet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CabinetMedical> findAll() {
        List<CabinetMedical> list = new ArrayList<>();
        String sql = "SELECT * FROM cabinet_medical ORDER BY nom";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToCabinet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CabinetMedical findByNom(String nom) {
        String sql = "SELECT * FROM cabinet_medical WHERE nom = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToCabinet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(CabinetMedical cabinet) {
        String sql = """
            UPDATE cabinet_medical SET
            nom = ?, email = ?, logo = ?, adresse = ?, tel1 = ?, tel2 = ?,
            site_web = ?, instagram = ?, facebook = ?, description = ?
            WHERE id = ?
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, cabinet.getNom());
            ps.setString(2, cabinet.getEmail());
            ps.setString(3, cabinet.getLogo());
            ps.setString(4, cabinet.getAdresse());
            ps.setString(5, cabinet.getTel1());
            ps.setString(6, cabinet.getTel2());
            ps.setString(7, cabinet.getSiteWeb());
            ps.setString(8, cabinet.getInstagram());
            ps.setString(9, cabinet.getFacebook());
            ps.setString(10, cabinet.getDescription());
            ps.setLong(11, cabinet.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM cabinet_medical WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CabinetMedical mapToCabinet(ResultSet rs) throws SQLException {
        CabinetMedical cabinet = new CabinetMedical();
        cabinet.setId(rs.getLong("id"));
        cabinet.setNom(rs.getString("nom"));
        cabinet.setEmail(rs.getString("email"));
        cabinet.setLogo(rs.getString("logo"));
        cabinet.setAdresse(rs.getString("adresse"));
        cabinet.setTel1(rs.getString("tel1"));
        cabinet.setTel2(rs.getString("tel2"));
        cabinet.setSiteWeb(rs.getString("site_web"));
        cabinet.setInstagram(rs.getString("instagram"));
        cabinet.setFacebook(rs.getString("facebook"));
        cabinet.setDescription(rs.getString("description"));
        return cabinet;
    }
}
