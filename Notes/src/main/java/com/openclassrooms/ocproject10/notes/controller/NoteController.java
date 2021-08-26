package com.openclassrooms.ocproject10.notes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.domain.Note;
import com.openclassrooms.ocproject10.notes.domain.PatientNote;
import com.openclassrooms.ocproject10.notes.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	static public String PATIENTSURL = "http://localhost:8081/"; // constant on capital letters

	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

	/* GET patient list view */
	@GetMapping(value = "/patient/list")
	public ModelAndView getAllPatients(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("patientList", noteService.getAllPatients());
		mav.setViewName("patient/patientList");
		log.info("LOG: Number of patients on list: " + noteService.getAllPatients().size());
		return mav;
	}

	/* GET api patient list */
	@RequestMapping("api/patient/list")
	public List<Patient> getAllPatientsApi() {
		return noteService.getAllPatients();
	}

	/* GET note list view */
	@GetMapping("/note/list/{patientId}")
	public ModelAndView getAllNotesByPatientId(@PathVariable("patientId") String patientId, Model model) {
		ModelAndView mav = new ModelAndView();
		if (patientId != null) {
			PatientNote patientNote = noteService.findNoteByPatientId(patientId);
			List<Note> notes = (patientNote != null) ? patientNote.getNotes() : new ArrayList<>();
			
			model.addAttribute("noteList", notes);
			model.addAttribute("patientId", patientId);
			mav.setViewName("note/list");
		}
		log.info("LOG: Number of note on list by patient id: " + noteService.findAllNotesByPatientId(patientId).size());
		return mav;
	}

	/* GET addNote view */
	@GetMapping("/note/add/{patientId}")
	public ModelAndView addNote(@PathVariable("patientId") String patientId, Model model) {
	    ModelAndView mav = new ModelAndView();
	    Note note = new Note();
	    
	    model.addAttribute("note", note);
	    model.addAttribute("patientId", patientId);
	    
	    mav.setViewName("note/add");
	    log.info("LOG: Show addNote form");
	    return mav;

	}

	/* POST save addNote */
	@PostMapping(value = "/note/save/{patientId}")
	public ModelAndView save(@PathVariable("patientId") String patientId, @Valid Note note, BindingResult result, Model model) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			model.addAttribute("note", note);
			model.addAttribute("patientId", patientId);
			mav.setViewName("note/add");
			return mav;
		}
		PatientNote patientNote = noteService.findNoteByPatientId(patientId);
		if (patientNote == null) {
			patientNote = new PatientNote(patientId);
		}
		patientNote.getNotes().add(note);
		noteService.createNote(patientNote);
		model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
		mav.addObject("patientId", patientId);
		mav.setViewName("redirect:/note/list/{patientId}");
		log.info("Add Note " + note.toString());
		return mav;
	}
	
	@GetMapping("/note/update/{patientId}")
	public ModelAndView showNoteUpdateForm(@PathVariable("patientId") String patientId, Model model) {
		ModelAndView mav = new ModelAndView();
		PatientNote patientNote = noteService.findNoteByPatientId(patientId);
		if (patientNote != null) {
		    Note note = patientNote.getNotes();
		    model.addAttribute("note", note);
			model.addAttribute("patient", patientNote);
			mav.setViewName("note/update");
			log.info("LOG: Show update form");
			return mav;
		}
		return mav;
	}
	
	

}
