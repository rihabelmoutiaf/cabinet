package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Facture;

import java.util.List;
import java.util.Optional;

public interface FactureRepository {

    List<Facture> findAll();

    Optional<Facture> findById(Long id);

    void create(Facture facture);

    void update(Facture facture);

    void delete(Facture facture);

    void deleteById(Long id);

    List<Facture> findByPatientId(Long patientId);
}
