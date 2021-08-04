package com.openclassrooms.ocproject10.reports.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.reports.service.ReportServiceImpl;

@Controller
public class ReportController {
	
	@Autowired
	private ReportServiceImpl reportServiceImpl;

	private static final Logger log = LoggerFactory.getLogger(ReportController.class);
	
	/* GET a list of patients */
	@GetMapping("/report/patientList")
	public String getAllPatients(Model model) {
		model.addAttribute("patientList", reportServiceImpl.findAllPatients());
		log.info("LOG: Number of patients on list: " + reportServiceImpl.findAllPatients().size());
		return "report/patientList";
	}
    
    @GetMapping(value = "/api/report/patientList", produces = "application/json")
	public ResponseEntity<List<Patient>> getPatientList() {
		return ResponseEntity.status(HttpStatus.OK).body(reportServiceImpl.findAllPatients());
	}

}
