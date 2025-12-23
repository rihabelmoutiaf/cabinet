
package ma.project.dentalTech.repository.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.AgendaMedecin;

import java.time.LocalDate;
import java.util.List;

public interface AgendaMedecinRepository {

    AgendaMedecin save(AgendaMedecin agenda);

    AgendaMedecin findById(Long id);

    List<AgendaMedecin> findByMedecinAndDate(Long medecinId, LocalDate date);

    List<AgendaMedecin> findAll();

    void delete(Long id);
}

