
package com.openclassrooms.ocproject10.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.openclassrooms.ocproject10.domain.Note;

public interface NoteRepository extends MongoRepository<Note, String> {

	List<Note> findNotesByPatientId(String patientId);

	Optional<Note> findById(String patientId);
	
}
