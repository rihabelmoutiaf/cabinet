package ma.project.dentalTech.mvc.controllers.modules.patient.batch_implentation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.project.dentalTech.configuration.ApplicationContext;
import ma.project.dentalTech.mvc.controllers.modules.patient.api.PatientController;
import ma.project.dentalTech.mvc.dto.PatientDTO;
import ma.project.dentalTech.service.modules.patient.api.PatientService;

@Data @AllArgsConstructor
public class PatientControllerImpl implements PatientController {

    private PatientService service;

    public PatientControllerImpl() {
        this.service = ApplicationContext.getInstance().patientService();
    }

    @Override
    public void showRecentPatients() {
        List<PatientDTO> dtos = service.getTodayPatientsAsDTO();
        if (dtos.isEmpty()) {
            System.out.println("Aucun patient ajouté aujourd'hui.");
            return;
        }
        System.out.println("=== Patients ajoutés aujourd'hui ===");
        dtos.forEach(dto -> System.out.printf("- %s | %d ans | ajouté le %s%n",
                dto.getNomComplet(), dto.getAge(), dto.getDateCreationFormatee()));
    }
}

