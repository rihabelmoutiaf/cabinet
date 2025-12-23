package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Notification;

import java.util.List;

public interface NotificationRepository {

    Notification save(Notification notification);

    Notification findById(Long id);

    List<Notification> findByUser(Long userId);

    List<Notification> findUnreadByUser(Long userId);

    List<Notification> findAll();

    void markAsRead(Long id);

    void delete(Long id);
}
