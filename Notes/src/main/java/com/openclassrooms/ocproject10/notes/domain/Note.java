package com.openclassrooms.ocproject10.notes.domain;

import java.util.UUID;

public class Note {
	
	private String id;
	
	private String noteText;
	
	public Note() {
		this.id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}


}
