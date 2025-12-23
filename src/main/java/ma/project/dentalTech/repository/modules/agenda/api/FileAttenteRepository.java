package ma.project.dentalTech.repository.modules.agenda.api;

import ma.project.dentalTech.entities.agenda.FileAttente;

import java.util.List;

public interface FileAttenteRepository {

    FileAttente save(FileAttente fileAttente);

    FileAttente findById(Long id);

    List<FileAttente> findAll();

    List<FileAttente> findByPatient(Long patientId);

    void updatePosition(Long id, int newPosition);

    void delete(Long id);

    void clear();
}
