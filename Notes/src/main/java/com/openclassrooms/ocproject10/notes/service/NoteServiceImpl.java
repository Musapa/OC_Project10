package com.openclassrooms.ocproject10.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.ocproject10.notes.domain.Note;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<Note> findAllNotesByPatientId(int patientId) {
		return noteRepository.findNotesByPatientId(patientId);
	}

	@Override
	public Note findNoteById(Integer id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (noteOptional.isPresent()) {
			Note note = noteOptional.get();
			return note;
		}
		return null;
	}

	@Override
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
	public void deleteNotetById(Integer id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (noteOptional.isPresent()) {
			noteRepository.delete(noteOptional.get());
		}
	}
}
