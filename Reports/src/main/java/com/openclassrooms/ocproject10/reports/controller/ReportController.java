package com.openclassrooms.ocproject10.reports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.domain.Report;
import com.openclassrooms.ocproject10.reports.service.ReportServiceImpl;

@RestController
public class ReportController {

	static public String PATIENTSURL = "http://localhost:8081/";
	static public String NOTESURL = "http://localhost:8082/";

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	ReportServiceImpl reportService;

	@GetMapping(value = "/patient/list")
	public ModelAndView getAllPatients(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("patientList", reportService.getAllPatients());
		mav.setViewName("patient/patientList");
		log.info("LOG: Number of patients on list: " + reportService.getAllPatients().size());
		return mav;
	}

	@GetMapping("/note/list/{patientId}")
	public ModelAndView getAllNotesByPatientId(@PathVariable("patientId") int patientId, Model model) {
		ModelAndView mav = new ModelAndView();
		if (patientId != 0) {
			List<Note> notes = reportService.findAllNotesByPatientId(patientId);
			model.addAttribute("noteList", notes);
			mav.setViewName("note/noteList");
		}
		log.info("LOG: Number of notes on list (by patient id): "
				+ reportService.findAllNotesByPatientId(patientId).size());
		return mav;
	}

	@GetMapping("/report/appearances/{patientId}")
	public ModelAndView showReportByPatientId(@PathVariable("patientId") int patientId, Model model) {
		ModelAndView mav = new ModelAndView();
		Patient patient = reportService.findPatientInListOfPatient(patientId);
		if (patientId != 0) {
			Report report = reportService.getReports(patientId);
			model.addAttribute("patient", patient);
			model.addAttribute("report", report);
			mav.setViewName("report/appearances");
		}
		log.info("LOG: Show reportByPatientId");
		return mav;
	}

}
