package com.openclassrooms.ocproject10.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ocproject10.notes.domain.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    
	List<Note> findNotesByPatientId(int patientId);
	
	Optional<Note> findById(Integer id);
}
