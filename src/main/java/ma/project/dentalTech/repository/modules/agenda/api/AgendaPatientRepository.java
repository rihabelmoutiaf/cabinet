package ma.project.dentalTech.repository.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.AgendaPatient;

import java.time.LocalDate;
import java.util.List;

public interface AgendaPatientRepository {

    AgendaPatient save(AgendaPatient agenda);

    AgendaPatient findById(Long id);

    List<AgendaPatient> findByPatient(Long patientId);

    List<AgendaPatient> findByPatientAndDate(Long patientId, LocalDate date);

    List<AgendaPatient> findAll();

    void delete(Long id);
}
