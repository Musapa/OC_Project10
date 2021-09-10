package com.openclassrooms.ocproject10.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Field("patientId")
	@NotBlank
	private String patientId;

	@Field("noteText")
	@NotEmpty(message = "NoteText is mandatory.")
	private String noteText;

	@Field("dateOfNote")
	@Temporal(TemporalType.DATE)
	private Date dateOfNote = new Date(System.currentTimeMillis());

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
