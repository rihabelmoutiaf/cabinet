package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Notification;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByUtilisateur(Utilisateur utilisateur);
}
