package ma.project.dentalTech.repository.modules.dossierMedical.api;

import ma.project.dentalTech.entities.dossierMedical.Prescription;
import ma.project.dentalTech.entities.dossierMedical.Medicament;
import ma.project.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface PrescriptionRepo extends CrudRepository<Prescription, Long> {

    List<Prescription> findByOrdonnanceId(Long ordonnanceId);

    List<Prescription> findByMedicamentId(Long medicamentId);

    List<Medicament> getMedicamentsByOrdonnance(Long ordonnanceId);
}
