package ma.project.dentalTech.service.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.AgendaPatient;
import ma.project.dentalTech.repository.modules.agenda.api.AgendaPatientRepository;
import ma.project.dentalTech.service.modules.agenda.api.AgendaPatientService;

import java.time.LocalDate;
import java.util.List;

public class AgendaPatientServiceImpl implements AgendaPatientService {

    private final AgendaPatientRepository agendaPatientRepository;

    public AgendaPatientServiceImpl(AgendaPatientRepository agendaPatientRepository) {
        this.agendaPatientRepository = agendaPatientRepository;
    }

    @Override
    public AgendaPatient createAgenda(AgendaPatient agenda) {
        // règles métier basiques
        if (agenda.getPatientId() == null || agenda.getRdvId() == null) {
            throw new IllegalArgumentException("Patient et RDV sont obligatoires");
        }
        if (agenda.getDate() == null || agenda.getHeure() == null) {
            throw new IllegalArgumentException("Date et heure sont obligatoires");
        }
        return agendaPatientRepository.save(agenda);
    }

    @Override
    public AgendaPatient getById(Long id) {
        return agendaPatientRepository.findById(id);
    }

    @Override
    public List<AgendaPatient> getByPatient(Long patientId) {
        return agendaPatientRepository.findByPatient(patientId);
    }

    @Override
    public List<AgendaPatient> getByPatientAndDate(Long patientId, LocalDate date) {
        return agendaPatientRepository.findByPatientAndDate(patientId, date);
    }

    @Override
    public List<AgendaPatient> getAll() {
        return agendaPatientRepository.findAll();
    }

    @Override
    public void deleteAgenda(Long id) {
        agendaPatientRepository.delete(id);
    }
}
