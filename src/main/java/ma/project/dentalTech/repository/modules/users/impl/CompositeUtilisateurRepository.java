package ma.project.dentalTech.repository.modules.users.impl;

import ma.project.dentalTech.entities.users.Admin;
import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.entities.users.Secretaire;
import ma.project.dentalTech.entities.users.Utilisateur;
import ma.project.dentalTech.repository.modules.users.api.AdminRepository;
import ma.project.dentalTech.repository.modules.users.api.MedecinRepository;
import ma.project.dentalTech.repository.modules.users.api.SecretaireRepository;
import ma.project.dentalTech.repository.modules.users.api.UtilisateurRepository;

import java.util.ArrayList;
import java.util.List;

public class CompositeUtilisateurRepository implements UtilisateurRepository<Utilisateur> {

    private final AdminRepository adminRepository;
    private final MedecinRepository medecinRepository;
    private final SecretaireRepository secretaireRepository;

    public CompositeUtilisateurRepository(
            AdminRepository adminRepository,
            MedecinRepository medecinRepository,
            SecretaireRepository secretaireRepository
    ) {
        this.adminRepository = adminRepository;
        this.medecinRepository = medecinRepository;
        this.secretaireRepository = secretaireRepository;
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        if (utilisateur instanceof Admin) {
            return adminRepository.save((Admin) utilisateur);
        }
        if (utilisateur instanceof Medecin) {
            return medecinRepository.save((Medecin) utilisateur);
        }
        if (utilisateur instanceof Secretaire) {
            return secretaireRepository.save((Secretaire) utilisateur);
        }
        throw new IllegalArgumentException("Unsupported user type: " + utilisateur.getClass().getName());
    }

    @Override
    public Utilisateur findById(Long id) {
        if (id == null) {
            return null;
        }
        Utilisateur user = adminRepository.findById(id);
        if (user != null) {
            return user;
        }
        user = medecinRepository.findById(id);
        if (user != null) {
            return user;
        }
        return secretaireRepository.findById(id);
    }

    @Override
    public Utilisateur findByEmail(String email) {
        if (email == null || email.isBlank()) {
            return null;
        }
        Utilisateur user = adminRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        user = medecinRepository.findByEmail(email);
        if (user != null) {
            return user;
        }
        return secretaireRepository.findByEmail(email);
    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> list = new ArrayList<>();
        list.addAll(adminRepository.findAll());
        list.addAll(medecinRepository.findAll());
        list.addAll(secretaireRepository.findAll());
        return list;
    }

    @Override
    public List<Utilisateur> findByRoleId(Long roleId) {
        List<Utilisateur> list = new ArrayList<>();
        if (roleId == null) {
            return list;
        }
        for (Utilisateur user : findAll()) {
            if (roleId.equals(user.getRoleId())) {
                list.add(user);
            }
        }
        return list;
    }

    @Override
    public void update(Utilisateur utilisateur) {
        if (utilisateur instanceof Admin) {
            adminRepository.update((Admin) utilisateur);
            return;
        }
        if (utilisateur instanceof Medecin) {
            medecinRepository.update((Medecin) utilisateur);
            return;
        }
        if (utilisateur instanceof Secretaire) {
            secretaireRepository.update((Secretaire) utilisateur);
            return;
        }
        throw new IllegalArgumentException("Unsupported user type: " + utilisateur.getClass().getName());
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        adminRepository.delete(id);
        medecinRepository.delete(id);
        secretaireRepository.delete(id);
    }
}
