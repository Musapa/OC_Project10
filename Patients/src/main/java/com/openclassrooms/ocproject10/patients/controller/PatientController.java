package com.openclassrooms.ocproject10.patients.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.service.PatientService;

@Controller
public class PatientController {
	
	@Autowired
	private PatientService patientService;

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	
	/* GET a list of patients */
	@GetMapping("/patient/list")
	public String getAllPatients(Model model) {
		model.addAttribute("patientList", patientService.findAllPatients());
		log.info("LOG: Number of patients on list: " + patientService.findAllPatients().size());
		return "patient/list";
	}
	
	@GetMapping("/patient/add")
	public String addPatient(Patient patient) {
		log.info("LOG: Add patient view");
		return "patient/add";
	}
	
	@PostMapping("/patient/save")
	public String save(@Valid Patient patient, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			Patient savedPatient = patientService.createPatient(patient);
			log.info("LOG: Patient save: id:" + savedPatient.getId() + " givenName: " + savedPatient.getGivenName() + " familyName: " + patient.getFamilyName());
			return "redirect:/patient/list";
		}
		log.error("LOG: Patient Validate error: " + result.getErrorCount() + " errors");
		
		return "patient/add";
	}
	
	@GetMapping("/patient/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Patient patient = patientService.findPatientById(id);
		 if (patient != null) {
			 model.addAttribute("patient", patient);
		}
		log.info("LOG: Show update form");
		return "patient/update";
		
	}

	@PostMapping("/patient/update/{id}")
	public String updatePatient(@PathVariable("id") Integer id, @Valid Patient patient, BindingResult result, Model model) {
		if (result.hasErrors()) {
			log.error("LOG: Patient update error: " + result.getErrorCount());
			return "patient/update";
		}
		patient.setId(id);
		patientService.updatePatient(patient);
		
		log.info("LOG: Patient update: id:" + patient.getId() + " givenName: " + patient.getGivenName() + " familyName: " + patient.getFamilyName());
		return "redirect:/patient/list";
	}

	@GetMapping("/patient/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		Patient patient = patientService.findPatientById(id);
		if (patient != null) {
			patientService.deletePatientById(id);
		}
		log.info("LOG: Patient delete: id:" + patient.getId() + " givenName: " + patient.getGivenName() + " familyName: " + patient.getFamilyName());
		return "redirect:/patient/list";
	}	
    
    @GetMapping(value = "/api/patient/list", produces = "application/json")
	public ResponseEntity<List<Patient>> getPatientList() {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatients());
	}

}
