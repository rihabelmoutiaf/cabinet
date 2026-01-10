package ma.project.dentalTech.service.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.AgendaMedecin;
import ma.project.dentalTech.repository.modules.agenda.api.AgendaMedecinRepository;
import ma.project.dentalTech.service.modules.agenda.api.AgendaMedecinService;

import java.time.LocalDate;
import java.util.List;

public class AgendaMedecinServiceImpl implements AgendaMedecinService {

    private final AgendaMedecinRepository agendaRepository;

    public AgendaMedecinServiceImpl(AgendaMedecinRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public AgendaMedecin createAgenda(AgendaMedecin agenda) {
        // règle métier simple
        if (agenda.getDate() == null || agenda.getMedecinId() == null) {
            throw new IllegalArgumentException("Date et médecin sont obligatoires");
        }
        return agendaRepository.save(agenda);
    }

    @Override
    public AgendaMedecin getById(Long id) {
        return agendaRepository.findById(id);
    }

    @Override
    public List<AgendaMedecin> getByMedecinAndDate(Long medecinId, LocalDate date) {
        return agendaRepository.findByMedecinAndDate(medecinId, date);
    }

    @Override
    public List<AgendaMedecin> getAll() {
        return agendaRepository.findAll();
    }

    @Override
    public void deleteAgenda(Long id) {
        agendaRepository.delete(id);
    }
}
