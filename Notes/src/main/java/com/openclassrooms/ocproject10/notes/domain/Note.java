package com.openclassrooms.ocproject10.notes.domain;

import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Field;

public class Note {
	
	@Field("note")
	@NotBlank(message = "Note field is mandatory.")
	private String note;
	
	public Note() {

	}

	public Note(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
