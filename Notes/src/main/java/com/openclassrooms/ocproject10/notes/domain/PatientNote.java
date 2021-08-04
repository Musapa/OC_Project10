package com.openclassrooms.ocproject10.notes.domain;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patientNote")
public class PatientNote {

	@Id
	private Integer patientId;
	
	private List<Note> noteList;
	
	
	
}
