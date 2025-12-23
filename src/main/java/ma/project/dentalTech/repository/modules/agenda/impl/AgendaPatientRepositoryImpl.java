package ma.project.dentalTech.repository.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.AgendaPatient;
import ma.project.dentalTech.repository.modules.agenda.api.AgendaPatientRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaPatientRepositoryImpl implements AgendaPatientRepository {

    private Connection connection;

    public AgendaPatientRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AgendaPatient save(AgendaPatient agenda) {
        String sql = "INSERT INTO agenda_patient (patient_id, rdv_id, date, heure) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, agenda.getPatientId());
            ps.setLong(2, agenda.getRdvId());
            ps.setDate(3, Date.valueOf(agenda.getDate()));
            ps.setTime(4, Time.valueOf(agenda.getHeure()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                agenda.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agenda;
    }

    @Override
    public AgendaPatient findById(Long id) {
        String sql = "SELECT * FROM agenda_patient WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToAgenda(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AgendaPatient> findByPatient(Long patientId) {
        List<AgendaPatient> list = new ArrayList<>();
        String sql = "SELECT * FROM agenda_patient WHERE patient_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToAgenda(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<AgendaPatient> findByPatientAndDate(Long patientId, LocalDate date) {
        List<AgendaPatient> list = new ArrayList<>();
        String sql = "SELECT * FROM agenda_patient WHERE patient_id = ? AND date = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToAgenda(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<AgendaPatient> findAll() {
        List<AgendaPatient> list = new ArrayList<>();
        String sql = "SELECT * FROM agenda_patient";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToAgenda(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM agenda_patient WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AgendaPatient mapToAgenda(ResultSet rs) throws SQLException {
        AgendaPatient agenda = new AgendaPatient();
        agenda.setId(rs.getLong("id"));
        agenda.setPatientId(rs.getLong("patient_id"));
        agenda.setRdvId(rs.getLong("rdv_id"));
        agenda.setDate(rs.getDate("date").toLocalDate());
        agenda.setHeure(rs.getTime("heure").toLocalTime());
        return agenda;
    }
}
