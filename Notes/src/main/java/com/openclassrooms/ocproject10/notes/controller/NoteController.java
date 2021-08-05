package com.openclassrooms.ocproject10.notes.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.ocproject10.notes.domain.Note;
import com.openclassrooms.ocproject10.notes.service.NoteService;
import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.service.PatientService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	@GetMapping("/note/list/{patientId}")
	public String getAllNotes(@PathVariable("patientId") Integer patientId, Model model) {
		if (patientId != null) {
			model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
			model.addAttribute("patientId", patientId);
			log.info("LOG: Number of notes on list: " + noteService.findAllNotesByPatientId(patientId).size());
		}
		log.info("LOG: There is no patients on list to add notes.");
		return "note/list";
	}

	@GetMapping("/note/add/{patientId}")
	public String addNote(@PathVariable("patientId") Integer patientId, Model model) {
		if (patientId != null) {
			Note note = new Note();
			note.setPatientId(patientId);
			model.addAttribute("note", note);
			log.info("LOG: Add note view");
		}
		return "note/add";
	}

	@PostMapping("/note/save/{patientId}")
	public String saveNote(@PathVariable("patientId") Integer patientId, @Valid Note note, BindingResult result,
			Model model) {
		if (patientId != null) {
			if (!result.hasErrors()) {
				Note savedNote = noteService.createNote(note);
				log.info("LOG: Note save: id:" + savedNote.getId() + " patient: " + savedNote.getPatientId() + " note: "
						+ savedNote.getNote());
				return "redirect:/note/list/{patientId}";
			}
		}
		log.error("LOG: Note Save error: " + result.getErrorCount() + " errors");
		return "note/add";
	}

}
