package ma.project.dentalTech.repository.modules.patient.api;
import ma.project.dentalTech.entities.patient.Antecedent;
import ma.project.dentalTech.entities.patient.Patient;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;




public interface AntecedentRepository extends CrudRepository<Antecedent, Long> {

    Optional<Antecedent> findByNom(String nom);

    boolean existsById(Long id);
    long count();
    List<Antecedent> findPage(int limit, int offset);


    List<Patient> getPatientsHavingAntecedent(Long antecedentId);
}
