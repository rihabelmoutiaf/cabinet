package ma.project.dentalTech.repository.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.FileAttente;
import ma.project.dentalTech.repository.modules.agenda.api.FileAttenteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileAttenteRepositoryImpl implements FileAttenteRepository {

    private Connection connection;

    public FileAttenteRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public FileAttente save(FileAttente fileAttente) {
        String sql = "INSERT INTO file_attente (patient_id, position) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, fileAttente.getPatientId());
            ps.setInt(2, fileAttente.getPosition());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                fileAttente.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileAttente;
    }

    @Override
    public FileAttente findById(Long id) {
        String sql = "SELECT * FROM file_attente WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToFileAttente(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FileAttente> findAll() {
        List<FileAttente> list = new ArrayList<>();
        String sql = "SELECT * FROM file_attente ORDER BY position";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToFileAttente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FileAttente> findByPatient(Long patientId) {
        List<FileAttente> list = new ArrayList<>();
        String sql = "SELECT * FROM file_attente WHERE patient_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToFileAttente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePosition(Long id, int newPosition) {
        String sql = "UPDATE file_attente SET position = ? WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, newPosition);
            ps.setLong(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM file_attente WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM file_attente";

        try (Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private FileAttente mapToFileAttente(ResultSet rs) throws SQLException {
        FileAttente fileAttente = new FileAttente();
        fileAttente.setId(rs.getLong("id"));
        fileAttente.setPatientId(rs.getLong("patient_id"));
        fileAttente.setPosition(rs.getInt("position"));
        return fileAttente;
    }
}
