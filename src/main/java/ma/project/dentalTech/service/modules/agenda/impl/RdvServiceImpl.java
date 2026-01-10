package ma.project.dentalTech.service.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.RDV;
import ma.project.dentalTech.entities.enums.StatutRDV;
import ma.project.dentalTech.repository.modules.agenda.api.RdvRepository;
import ma.project.dentalTech.service.modules.agenda.api.RdvService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RdvServiceImpl implements RdvService {

    private final RdvRepository rdvRepository;

    public RdvServiceImpl(RdvRepository rdvRepository) {
        this.rdvRepository = rdvRepository;
    }

    @Override
    public void create(RDV rdv) {
        // validations métier
        if (rdv.getPatientId() == null || rdv.getMedecinId() == null) {
            throw new IllegalArgumentException("Patient et médecin sont obligatoires");
        }
        if (rdv.getDateHeure() == null) {
            throw new IllegalArgumentException("Date et heure du RDV sont obligatoires");
        }

        // statut par défaut
        if (rdv.getStatut() == null) {
            rdv.setStatut(StatutRDV.PLANIFIE);
        }

        rdvRepository.create(rdv);
    }

    @Override
    public void update(RDV rdv) {
        if (rdv.getId() == null) {
            throw new IllegalArgumentException("ID du RDV obligatoire pour update");
        }
        rdvRepository.update(rdv);
    }

    @Override
    public Optional<RDV> getById(Long id) {
        return rdvRepository.findById(id);
    }

    @Override
    public List<RDV> getAll() {
        return rdvRepository.findAll();
    }

    @Override
    public List<RDV> getByPatient(Long patientId) {
        return rdvRepository.findByPatientId(patientId);
    }

    @Override
    public List<RDV> getByMedecin(Long medecinId) {
        return rdvRepository.findByMedecinId(medecinId);
    }

    @Override
    public List<RDV> getByDateBetween(LocalDateTime start, LocalDateTime end) {
        return rdvRepository.findByDateBetween(start, end);
    }

    @Override
    public List<RDV> getTodayRdv() {
        return rdvRepository.findTodayRdv();
    }

    @Override
    public void delete(Long id) {
        rdvRepository.deleteById(id);
    }
}
