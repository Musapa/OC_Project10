package com.openclassrooms.ocproject10.notes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ocproject10.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	@GetMapping("/note/list/{patientId}")
	public String getAllNotes(@PathVariable("patientId") Integer patientId, Model model) {		
		Patient patient = patientService.findPatientById(id);	
		if (patient != null) {
			model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
			model.addAttribute("patientId", patientId);
			log.info("LOG: Number of notes on list: " + noteService.findAllNotesByPatientId(patientId).size());
			return "note/list";
		}
		log.info("LOG: There is no patients on list.");
	}
}
