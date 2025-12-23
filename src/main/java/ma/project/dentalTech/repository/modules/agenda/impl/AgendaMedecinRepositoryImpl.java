package ma.project.dentalTech.repository.modules.agenda.impl;


import ma.project.dentalTech.entities.agenda.AgendaMedecin;
import ma.project.dentalTech.repository.modules.agenda.api.AgendaMedecinRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendaMedecinRepositoryImpl implements AgendaMedecinRepository {

    private Connection connection;

    public AgendaMedecinRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AgendaMedecin save(AgendaMedecin agenda) {
        String sql = "INSERT INTO agenda_medecin (medecin_id, date, heure_debut, heure_fin, disponible) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, agenda.getMedecinId());
            ps.setDate(2, Date.valueOf(agenda.getDate()));
            ps.setTime(3, Time.valueOf(agenda.getHeureDebut()));
            ps.setTime(4, Time.valueOf(agenda.getHeureFin()));
            ps.setBoolean(5, agenda.isDisponible());

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
    public AgendaMedecin findById(Long id) {
        String sql = "SELECT * FROM agenda_medecin WHERE id = ?";
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
    public List<AgendaMedecin> findByMedecinAndDate(Long medecinId, LocalDate date) {
        List<AgendaMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM agenda_medecin WHERE medecin_id = ? AND date = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, medecinId);
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
    public List<AgendaMedecin> findAll() {
        List<AgendaMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM agenda_medecin";

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
        String sql = "DELETE FROM agenda_medecin WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private AgendaMedecin mapToAgenda(ResultSet rs) throws SQLException {
        AgendaMedecin agenda = new AgendaMedecin();
        agenda.setId(rs.getLong("id"));
        agenda.setMedecinId(rs.getLong("medecin_id"));
        agenda.setDate(rs.getDate("date").toLocalDate());
        agenda.setHeureDebut(rs.getTime("heure_debut").toLocalTime());
        agenda.setHeureFin(rs.getTime("heure_fin").toLocalTime());
        agenda.setDisponible(rs.getBoolean("disponible"));
        return agenda;
    }
}
