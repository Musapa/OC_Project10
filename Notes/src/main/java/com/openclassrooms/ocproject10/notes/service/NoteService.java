package com.openclassrooms.ocproject10.notes.service;

import java.util.List;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.domain.Note;

public interface NoteService {

	List<Patient> getAllPatients();
	
	List<Note> findAllNotesByPatientId(String patientId);

	Note findNoteByPatientId(String patientId);
	
	Note createNote(Note note);

	void updateNote(Note note);
	
	void deleteNote(String noteId);


	
}
