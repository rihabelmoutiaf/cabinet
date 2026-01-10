package ma.project.dentalTech.repository.test;

import ma.project.dentalTech.entities.agenda.RDV;
import ma.project.dentalTech.entities.dossierMedical.Consultation;
import ma.project.dentalTech.entities.enums.StatutRDV;
import ma.project.dentalTech.repository.modules.agenda.api.RdvRepository;
import ma.project.dentalTech.repository.modules.agenda.impl.RdvRepositoryImpl;
import ma.project.dentalTech.repository.modules.dossierMedical.impl.ConsultationRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TestRepo {

    public static void main(String[] args) {
        RdvRepository rdvRepository = new RdvRepositoryImpl();

        RDV rdv = new RDV();
        rdv.setPatientId(1L);
        rdv.setMedecinId(2L);
        rdv.setDateHeure(LocalDateTime.now().plusDays(1));
        rdv.setMotif("Consultation test");
        rdv.setStatut(StatutRDV.PLANIFIE);

        rdvRepository.create(rdv);
        System.out.println("RDV créé avec ID = " + rdv.getId());


        Optional<RDV> found = rdvRepository.findById(rdv.getId());
        System.out.println("RDV trouvé : " + found.orElse(null));


        List<RDV> all = rdvRepository.findAll();
        System.out.println("Nombre total RDV = " + all.size());


        List<RDV> byPatient = rdvRepository.findByPatientId(1L);
        System.out.println("RDV du patient 1 = " + byPatient.size());


        List<RDV> byMedecin = rdvRepository.findByMedecinId(2L);
        System.out.println("RDV du médecin 2 = " + byMedecin.size());


        List<RDV> today = rdvRepository.findTodayRdv();
        System.out.println("RDV aujourd'hui = " + today.size());


        rdvRepository.deleteById(rdv.getId());
        System.out.println("RDV supprimé");
    }
}
