package com.openclassrooms.ocproject10.notes.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PatientNote")
public class PatientNote {
	
	@Field("patientId")
	private String patientId;

	@Field("notes")
	private List<Note> notes;
	
	public PatientNote() {
		this.notes = new ArrayList<>();
	}

	public PatientNote(String patientId) {
		this.patientId = patientId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

}
