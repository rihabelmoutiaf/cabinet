package ma.project.dentalTech.mvc.controllers.modules.patient.swing_implementation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import ma.project.dentalTech.configuration.ApplicationContext;
import ma.project.dentalTech.mvc.controllers.modules.patient.api.PatientController;
import ma.project.dentalTech.mvc.dto.PatientDTO;
import ma.project.dentalTech.mvc.ui.modules.patient.PatientView;
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
        PatientView.showAsync(dtos);
    }
}

