package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Staff;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Long> {

    /**
     * Trouve un membre du staff par son CIN
     */
    Staff findByCin(String cin);

    /**
     * Trouve tout le staff actif
     */
    List<Staff> findAllActive();

    /**
     * Trouve le staff embauché entre deux dates
     */
    List<Staff> findByDateEmbaucheBetween(LocalDate start, LocalDate end);

    /**
     * Vérifie si un CIN existe déjà
     */
    boolean existsByCin(String cin);

    /**
     * Compte le nombre total de staff
     */
    long countStaff();
}