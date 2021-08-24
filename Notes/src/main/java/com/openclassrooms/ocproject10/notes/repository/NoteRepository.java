
package com.openclassrooms.ocproject10.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.openclassrooms.ocproject10.notes.domain.PatientNote;

public interface NoteRepository extends MongoRepository<PatientNote, String> {

	List<PatientNote> findNotesByPatientId(String patientId);

	Optional<PatientNote> findById(String patientId);
}
