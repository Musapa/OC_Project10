package com.openclassrooms.ocproject10.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "note")
public class Note {

	@Id
	private String id;

	@Field("patientId")
	private String patientId;

	@Field("noteText")
	private String noteText;

	@Field("dateOfNote")
	private Date dateOfNote;

	public Note() {
	}
	
	public Note(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}

	public Date getDateOfNote() {
		return dateOfNote;
	}

	public void setDateOfNote(Date dateOfNote) {
		this.dateOfNote = dateOfNote;
	}
	
	

}
