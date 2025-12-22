package ma.project.dentalTech.service.modules.patient.api;



import java.util.List;
import ma.project.dentalTech.mvc.dto.PatientDTO;

public interface PatientService {


    List<PatientDTO> getTodayPatientsAsDTO();
}