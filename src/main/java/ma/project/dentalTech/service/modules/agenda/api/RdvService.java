package ma.project.dentalTech.service.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.RDV;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RdvService {

    void create(RDV rdv);

    void update(RDV rdv);

    Optional<RDV> getById(Long id);

    List<RDV> getAll();

    List<RDV> getByPatient(Long patientId);

    List<RDV> getByMedecin(Long medecinId);

    List<RDV> getByDateBetween(LocalDateTime start, LocalDateTime end);

    List<RDV> getTodayRdv();

    void delete(Long id);
}
