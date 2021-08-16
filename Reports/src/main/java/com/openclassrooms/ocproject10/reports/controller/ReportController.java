package com.openclassrooms.ocproject10.reports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.reports.service.ReportService;

@RestController
public class ReportController {

	static public String PATIENTSURL = "http://localhost:8081/"; // constant on capital letters

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	ReportService reportService;
	
	@GetMapping(value = "/patient/list")
	public ModelAndView getAllPatients(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("patientList", reportService.getAllPatients());
		mav.setViewName("patient/patientList");
		log.info("LOG: Number of patients on list: " + reportService.getAllPatients().size());
		return mav;
	}
	
	@RequestMapping("api/patient/list")
	public List<Patient> getAllPatientsApi() {
		return reportService.getAllPatients();
	}
	
}
