package ma.project.dentalTech.repository.modules.agenda.api;
import ma.project.dentalTech.entities.agenda.RDV;
import ma.project.dentalTech.repository.common.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface RDVRepository extends CrudRepository<RDV, Long> {


    void delete(RDV r);

    List<RDV> findByPatientId(Long patientId);
    List<RDV> findByMedecinId(Long medecinId);
    List<RDV> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<RDV> findTodayRdv();

}