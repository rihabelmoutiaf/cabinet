package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Admin;

import java.util.List;

public interface AdminRepository {

    Admin save(Admin admin);

    Admin findById(Long id);

    Admin findByEmail(String email);

    Admin findByLogin(String login);

    List<Admin> findAll();

    void update(Admin admin);

    void delete(Long id);
}
