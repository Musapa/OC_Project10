package com.openclassrooms.ocproject10.notes.service;

import java.util.List;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.domain.PatientNote;

public interface NoteService {

	List<Patient> getAllPatients();
	
	List<PatientNote> findAllNotesByPatientId(String patientId);

	PatientNote findNoteByPatientId(String patientId);
	
	PatientNote createNote(PatientNote note);

	void deleteNote(String noteId);

	
}
