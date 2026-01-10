package ma.project.dentalTech.service.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.AgendaMedecin;

import java.time.LocalDate;
import java.util.List;

public interface AgendaMedecinService {

    AgendaMedecin createAgenda(AgendaMedecin agenda);

    AgendaMedecin getById(Long id);

    List<AgendaMedecin> getByMedecinAndDate(Long medecinId, LocalDate date);

    List<AgendaMedecin> getAll();

    void deleteAgenda(Long id);
}
