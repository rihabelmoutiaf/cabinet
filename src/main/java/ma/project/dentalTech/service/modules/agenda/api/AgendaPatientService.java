package ma.project.dentalTech.service.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.AgendaPatient;

import java.time.LocalDate;
import java.util.List;

public interface AgendaPatientService {

    AgendaPatient createAgenda(AgendaPatient agenda);

    AgendaPatient getById(Long id);

    List<AgendaPatient> getByPatient(Long patientId);

    List<AgendaPatient> getByPatientAndDate(Long patientId, LocalDate date);

    List<AgendaPatient> getAll();

    void deleteAgenda(Long id);
}
