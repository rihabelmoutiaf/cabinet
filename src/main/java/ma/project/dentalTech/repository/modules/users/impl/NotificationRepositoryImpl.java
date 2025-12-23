package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Notification;
import ma.project.dentalTech.repository.modules.users.api.NotificationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository {

    private Connection connection;

    public NotificationRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Notification save(Notification notification) {
        String sql = "INSERT INTO notification (user_id, message, lue, date_envoi) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, notification.getUserId());
            ps.setString(2, notification.getMessage());
            ps.setBoolean(3, notification.isLue());
            ps.setTimestamp(4, Timestamp.valueOf(notification.getDateEnvoi()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                notification.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notification;
    }

    @Override
    public Notification findById(Long id) {
        String sql = "SELECT * FROM notification WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToNotification(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Notification> findByUser(Long userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE user_id = ? ORDER BY date_envoi DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToNotification(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Notification> findUnreadByUser(Long userId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE user_id = ? AND lue = false ORDER BY date_envoi DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapToNotification(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Notification> findAll() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification ORDER BY date_envoi DESC";

        try (Statement st = connection.createStatement()) {

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapToNotification(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void markAsRead(Long id) {
        String sql = "UPDATE notification SET lue = true WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM notification WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Notification mapToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getLong("id"));
        notification.setUserId(rs.getLong("user_id"));
        notification.setMessage(rs.getString("message"));
        notification.setLue(rs.getBoolean("lue"));

        Timestamp ts = rs.getTimestamp("date_envoi");
        if (ts != null) {
            notification.setDateEnvoi(ts.toLocalDateTime());
        }

        return notification;
    }
}
