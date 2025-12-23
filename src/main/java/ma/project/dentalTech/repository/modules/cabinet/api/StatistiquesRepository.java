package ma.project.dentalTech.repository.modules.cabinet.api;

import ma.project.dentalTech.entities.cabinet.Statistiques;

public interface StatistiquesRepository {

    Statistiques calculer(Long cabinetId, java.time.LocalDate dateDebut, java.time.LocalDate dateFin);
}
