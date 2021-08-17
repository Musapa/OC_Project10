package com.openclassrooms.ocproject10.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.controller.NoteController;
import com.openclassrooms.ocproject10.notes.domain.PatientNote;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@Service("noteService")
public class NoteServiceImpl implements NoteService{
	
	@Bean
	public RestTemplate restTemplateNotes() {
	    return new RestTemplate();
	}
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NoteRepository noteRepository;

	public List<Patient> getAllPatients() {
		ResponseEntity<List<Patient>> response = restTemplateNotes()
				.exchange(getPatientsUrl() + "api/patient/list", HttpMethod.GET, null,new ParameterizedTypeReference<List<Patient>>() {});
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
	public List<PatientNote> findAllNotesByPatientId(String patientId) {
		return noteRepository.findNotesByPatientId(patientId);
	}
	
	@Override
	public PatientNote findNoteById(String patientId) {
		Optional<PatientNote> noteOptional = noteRepository.findById(patientId);
		if (noteOptional.isPresent()) {
			return noteOptional.get();
		}
		return null;
	}
	
	public PatientNote createNote(PatientNote note) {
		return noteRepository.save(note);
	}
	
	/*
	@Override
	public PatientNote createNote(PatientNote note) {
		return noteRepository.save(note);
	}

	@Override
	public void updateNote(PatientNote note) {
		Optional<PatientNote> noteOptional = noteRepository.findById(note.getId());
		if (noteOptional.isPresent()) {
			noteRepository.save(note);
		}
	}

	@Override
	public void deleteNote(Integer id) {
		Optional<PatientNote> noteOptional = noteRepository.findById(id);
		if (noteOptional.isPresent()) {
			noteRepository.delete(noteOptional.get());
		}
	}*/
	
}
