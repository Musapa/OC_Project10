
package com.openclassrooms.ocproject10.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.ocproject10.notes.domain.PatientNote;

@Repository
public interface NoteRepository extends MongoRepository<PatientNote, String> {

	List<PatientNote> findNotesByPatientId(String patientId);

	Optional<PatientNote> findById(String patientId);
}
