package ma.project.dentalTech.service.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.FileAttente;

import java.util.List;

public interface FileAttenteService {

    FileAttente addPatient(FileAttente fileAttente);

    FileAttente getById(Long id);

    List<FileAttente> getAll();

    List<FileAttente> getByPatient(Long patientId);

    void updatePosition(Long id, int newPosition);

    void remove(Long id);

    void clearFile();
}
