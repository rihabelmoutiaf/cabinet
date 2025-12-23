package ma.project.dentalTech.repository.modules.users.api;

import ma.project.dentalTech.entities.users.Secretaire;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface SecretaireRepository extends CrudRepository<Secretaire, Long> {

    /**
     * Trouve une secrétaire par son numéro CNSS
     */
    Secretaire findByNumCNSS(String numCNSS);

    /**
     * Trouve toutes les secrétaires actives
     */
    List<Secretaire> findAllActive();

    /**
     * Vérifie si un numéro CNSS existe déjà
     */
    boolean existsByNumCNSS(String numCNSS);

    /**
     * Met à jour les informations spécifiques de la secrétaire
     */
    void updateSecretaireInfo(Secretaire secretaire);

    /**
     * Compte le nombre total de secrétaires
     */
    long countSecretaires();
}
