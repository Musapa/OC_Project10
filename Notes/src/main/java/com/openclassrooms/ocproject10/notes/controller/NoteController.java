package com.openclassrooms.ocproject10.notes.controller;

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
			List<Note> notes = noteService.findAllNotesByPatientId(patientId);
			model.addAttribute("noteList", notes);
			mav.setViewName("note/list");
		}
		log.info("LOG: Number of notes on list (by patient id): " + noteService.findAllNotesByPatientId(patientId).size());
		return mav;
	}

	/* GET addNote view */
	@GetMapping("/note/add/{patientId}")
	public ModelAndView addNoteForm(@PathVariable("patientId") String patientId, Model model) {
		ModelAndView mav = new ModelAndView();
		if (patientId != null) {
			Note note = new Note();
            note.setPatientId(patientId);
			model.addAttribute("note", note);
			mav.setViewName("note/add");
			log.info("LOG: Show addNote view");
		}
		return mav;
	}

	/* POST save addNote */
	@PostMapping(value = "/note/save/{patientId}")
	public ModelAndView save(@Valid Note note, BindingResult result, Model model) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			model.addAttribute("note", note);
			mav.setViewName("note/add");
			return mav;
		}
		noteService.createNote(note);
		mav.setViewName("redirect:/note/list/{patientId}");
		log.info("LOG: Note save: id:" + note.getId() + " noteText:" + note.getNoteText());
		return mav;
	}

	/* GET update note view */
	@GetMapping("/note/update/{patientId}/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") String id, Model model) {
		ModelAndView mav = new ModelAndView();
		Note note = noteService.findNoteByPatientId(id);
		if (note != null) {
			model.addAttribute("note", note);
            mav.addObject("patientId", note.getPatientId());
			mav.setViewName("note/update");
			log.info("LOG: Show update note form");
			return mav;
		}
		return mav;
	}

	/* POST save update note */
	@PostMapping("/note/update/{patientId}/{id}")
	public ModelAndView updateNote(@Valid Note note, BindingResult result, Model model) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName("note/update");
			return mav;
		}
		noteService.updateNote(note);
		mav.setViewName("redirect:/note/list/{patientId}");
		log.info("LOG: Patient update: id:" + note.getId() + " noteText:" + note.getNoteText());
		return mav;
	}

	/* GET delete note */
	@GetMapping("/note/delete/{patientId}/{id}")
	public ModelAndView deleteNote(@PathVariable("id") String id, Model model) {
		ModelAndView mav = new ModelAndView();
		Note note = noteService.findNoteByPatientId(id);
		if (note != null) {
			noteService.deleteNote(id);
			mav.setViewName("redirect:/note/list/{patientId}");
			log.info("LOG: Note delete: id:" + note.getId() + " noteText:" + note.getNoteText());
			return mav;
		}
		return mav;
	}
	
	/* GET view note */
	@GetMapping("/note/view/{patientId}/{id}")
	public ModelAndView showViewForm(@PathVariable("id") String id, Model model) {
		ModelAndView mav = new ModelAndView();
		Note note = noteService.findNoteByPatientId(id);
		if (note != null) {
			model.addAttribute("note", note);
            mav.addObject("patientId", note.getPatientId());
			mav.setViewName("note/view");
			log.info("LOG: Show update note form");
			return mav;
		}
		return mav;
	}
	
}
