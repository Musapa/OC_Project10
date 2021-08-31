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
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.patients.service.PatientService;

@Controller
public class PatientController {

	@Autowired
	private PatientService patientService;

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);

	/* GET a list of patients */
	@GetMapping(value = "/patient/list")
	public ModelAndView getAllPatients(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("patientList", patientService.findAllPatients());
		mav.setViewName("patient/list");
		log.info("LOG: Number of patients on list: " + patientService.findAllPatients().size());
		return mav;
	}

	/* GET add patients view */
	@GetMapping("/patient/add")
	public ModelAndView addPatientForm(Model model) {
		ModelAndView mav = new ModelAndView();
		model.addAttribute("patient", new Patient());
		mav.setViewName("patient/add");
		log.info("LOG: Show addPatient view");
		return mav;
	}

	/* POST save addPatients */
	@PostMapping(value = "/patient/save")
	public ModelAndView save(@Valid Patient patient, BindingResult result, Model model) {
		ModelAndView mav = new ModelAndView();
		if (!result.hasErrors()) {
			Patient savedPatient = patientService.createPatient(patient);
			model.addAttribute("patientList", patientService.findAllPatients());
			mav.setViewName("redirect:/patient/list");
			log.info("LOG: Patient save: id:" + savedPatient.getId() + " givenName: " + savedPatient.getGivenName()
					+ " familyName: " + patient.getFamilyName());
			return mav;
		}
		mav.setViewName("patient/add");
		return mav;
	}

	/* GET update patients view */
	@GetMapping("/patient/update/{id}")
	public ModelAndView showUpdateForm(@PathVariable("id") Integer id, Model model) {
		ModelAndView mav = new ModelAndView();
		Patient patient = patientService.findPatientById(id);
		if (patient != null) {
			model.addAttribute("patient", patient);
			mav.setViewName("patient/update");
			log.info("LOG: Show update form");
			return mav;
		}
		return mav;
	}

	/* POST save update patients */
	@PostMapping("/patient/update/{id}")
	public ModelAndView updatePatient(@PathVariable("id") Integer id, @Valid Patient patient, BindingResult result, Model model) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName("patient/update");
			return mav;
		}
		patient.setId(id);
		patientService.updatePatient(patient);
		model.addAttribute("patient", patientService.findAllPatients());
		mav.setViewName("redirect:/patient/list");
		log.info("LOG: Patient update: id:" + patient.getId() + " givenName: " + patient.getGivenName()
				+ " familyName: " + patient.getFamilyName());
		return mav;
	}

	/* GET delete patient */
    @GetMapping("/patient/delete/{id}")
    public ModelAndView deletePatient(@PathVariable("id") Integer id, Model model) {
        ModelAndView mav = new ModelAndView();
        Patient patient = patientService.findPatientById(id);
        if (patient != null) {
            patientService.deletePatientById(id);
            model.addAttribute("patient", patientService.findAllPatients());
            mav.setViewName("redirect:/patient/list");
    		log.info("LOG: Patient delete: id:" + patient.getId() + " givenName: " + patient.getGivenName() + " familyName: " + patient.getFamilyName());
            return mav;
        }
        return mav;
    }

	@GetMapping(value = "/api/patient/list", produces = "application/json")
	public ResponseEntity<List<Patient>> getPatientList() {
		return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatients());
	}

}
