package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Medecin;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface MedecinRepository extends CrudRepository<Medecin, Long> {

    /**
     * Trouve un médecin par son numéro d'ordre
     */
    Medecin findByNumeroOrdre(String numeroOrdre);

    /**
     * Trouve un médecin par son numéro INPE
     */
    Medecin findByNumeroInpe(String numeroInpe);

    /**
     * Trouve tous les médecins par spécialité
     */
    List<Medecin> findBySpecialite(String specialite);

    /**
     * Trouve tous les médecins actifs
     */
    List<Medecin> findAllActive();

    /**
     * Vérifie si un numéro d'ordre existe déjà
     */
    boolean existsByNumeroOrdre(String numeroOrdre);

    /**
     * Vérifie si un numéro INPE existe déjà
     */
    boolean existsByNumeroInpe(String numeroInpe);

    /**
     * Met à jour les informations spécifiques du médecin
     */
    void updateMedecinInfo(Medecin medecin);
}