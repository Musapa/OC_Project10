package com.openclassrooms.ocproject10.service;

import java.util.List;

import com.openclassrooms.ocproject10.notes.domain.Note;

public interface NoteService {

	List<Note> findAllNotesByPatientId(int patientId);

	Note findNoteById(Integer id);

	Note createNote(Note note);

	void updateNote(Note note);

	void deleteNotetById(Integer id);

}
