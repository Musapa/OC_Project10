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
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.notes.domain.Note;
import com.openclassrooms.ocproject10.notes.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	private static final Logger log = LoggerFactory.getLogger(NoteController.class);

    @GetMapping("/note/list/{patientId}")
    public ModelAndView getAllNotesByPatientId(@PathVariable("patientId") Integer patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        if (patientId != null) {
            model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
            model.addAttribute("patientId", patientId);
            mav.setViewName("note/list");
        }
		log.info("LOG: Number of note on list by patient id: " + noteService.findAllNotesByPatientId(patientId).size());
        return mav;
    }
    
    @GetMapping("/note/add/{patientId}")
    public ModelAndView addNote(@PathVariable("patientId") Integer patientId, Model model) {
        ModelAndView mav = new ModelAndView();
        if (patientId != null) {
            Note note = new Note();
            note.setPatientId(patientId);
            model.addAttribute("note", note);
            mav.setViewName("note/add");
        }
        log.info("LOG: Add note view");
        return mav;
    }
	
    @PostMapping(value = "/note/save/{patientId}")
    public ModelAndView saveNote(@PathVariable("patientId") Integer patientId, @Valid Note note, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (patientId != null) {
            if (!result.hasErrors()) {
            	Note savedNote = noteService.createNote(note);
                model.addAttribute("noteList", noteService.findAllNotesByPatientId(patientId));
                mav.addObject("patientId", patientId);
                mav.setViewName("redirect:/note/list/{patientId}");
				log.info("LOG: Note save: id:" + savedNote.getId() + " note: " + savedNote.getNote());
                return mav;
            }
        }
        log.error("LOG: Note Save error: " + result.getErrorCount() + " errors");
        mav.setViewName("note/add");
        return mav;
    }
    
    @GetMapping("/note/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findNoteById(id);
        if (note != null) {
            model.addAttribute("note", note);
            mav.addObject("id", note.getId());
            mav.addObject("patientId", note.getPatientId());
            mav.setViewName("note/update");
            log.info("LOG: Show note update form");
            return mav;
        }
        return mav;
    }
    
    @PostMapping("/note/update/{id}")
    public ModelAndView updateNote(@PathVariable("id") Integer id, @Valid Note note, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("note/update");
            return mav;
        }
        note.setId(note.getId());
        noteService.updateNote(note);
        model.addAttribute("noteList", noteService.findAllNotesByPatientId(note.getPatientId()));
        mav.addObject("patientId", note.getPatientId());
        mav.setViewName("redirect:/note/list/{patientId}");
		log.info("LOG: Note update: id:" + note.getId() + " note: " + note.getNote());
        return mav;
    }
    
    @GetMapping("/note/delete/{id}")
    public ModelAndView deleteNote(@PathVariable("id") Integer id, Model model) {
        ModelAndView mav = new ModelAndView();
        Note note = noteService.findNoteById(id);
        if (note != null) {
            noteService.deleteNote(id);
            mav.addObject("patientId", note.getPatientId());
            model.addAttribute("noteList", noteService.findAllNotesByPatientId(note.getPatientId()));
            mav.setViewName("redirect:/note/list/{patientId}");
    		log.info("LOG: Note delete: id:" + note.getId() + " note: " + note.getNote());
            return mav;
        }
        return mav;
    }
    

}
