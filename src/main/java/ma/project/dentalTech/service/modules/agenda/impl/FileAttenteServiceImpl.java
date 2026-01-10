package ma.project.dentalTech.service.modules.agenda.impl;

import ma.project.dentalTech.entities.agenda.FileAttente;
import ma.project.dentalTech.repository.modules.agenda.api.FileAttenteRepository;
import ma.project.dentalTech.service.modules.agenda.api.FileAttenteService;

import java.util.List;

public class FileAttenteServiceImpl implements FileAttenteService {

    private final FileAttenteRepository fileAttenteRepository;

    public FileAttenteServiceImpl(FileAttenteRepository fileAttenteRepository) {
        this.fileAttenteRepository = fileAttenteRepository;
    }

    @Override
    public FileAttente addPatient(FileAttente fileAttente) {
        if (fileAttente.getPatientId() == null) {
            throw new IllegalArgumentException("Patient obligatoire pour la file d'attente");
        }

        // position auto = dernier + 1
        int nextPosition = fileAttenteRepository.findAll().size() + 1;
        fileAttente.setPosition(nextPosition);

        return fileAttenteRepository.save(fileAttente);
    }

    @Override
    public FileAttente getById(Long id) {
        return fileAttenteRepository.findById(id);
    }

    @Override
    public List<FileAttente> getAll() {
        return fileAttenteRepository.findAll();
    }

    @Override
    public List<FileAttente> getByPatient(Long patientId) {
        return fileAttenteRepository.findByPatient(patientId);
    }

    @Override
    public void updatePosition(Long id, int newPosition) {
        fileAttenteRepository.updatePosition(id, newPosition);
    }

    @Override
    public void remove(Long id) {
        fileAttenteRepository.delete(id);
    }

    @Override
    public void clearFile() {
        fileAttenteRepository.clear();
    }
}
