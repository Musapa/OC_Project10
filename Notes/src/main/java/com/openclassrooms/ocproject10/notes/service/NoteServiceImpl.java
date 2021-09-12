package com.openclassrooms.ocproject10.notes.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.controller.NoteController;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<Patient> getAllPatients() {
		ResponseEntity<List<Patient>> response = restTemplate.exchange(getPatientsUrl() + "api/patient/list",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Patient>>() {
				});
		return response.getBody();
	}

	private String getPatientsUrl() {
		String url = env.getProperty("PATIENTS_URL");
		if (url == null) {
			url = NoteController.PATIENTSURL;
		}
		return url;
	}

	@Override
	public List<Note> findAllNotesByPatientId(String patientId) {
		return noteRepository.findNotesByPatientId(patientId);
	}

	@Override
	public Note findNoteByPatientId(String patientId) {
		Optional<Note> noteOptional = noteRepository.findById(patientId);
		if (noteOptional.isPresent()) {
			return noteOptional.get();
		}
		return null;
	}

	public Note createNote(Note note) {
		return noteRepository.save(note);
	}

	@Override
	public void updateNote(Note note) {
		Optional<Note> noteOptional = noteRepository.findById(note.getId());
		if (noteOptional.isPresent()) {
			noteRepository.save(note);
		}
	}

	@Override
	public void deleteNote(String noteId) {
		Optional<Note> noteOptional = noteRepository.findById(noteId);
		if (noteOptional.isPresent()) {
			noteRepository.delete(noteOptional.get());
		} else {
			throw new NoSuchElementException("Something is wrong");
		}
	}

	@Override
	public List<Note> getNumberOfTriggerTermsOnNoteList(String patientId) {
		List<Note> note = noteRepository.findNotesByPatientId(patientId);
		return note;
	}

}
