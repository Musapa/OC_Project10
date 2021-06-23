package com.openclassrooms.ocproject10.patients.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.service.PatientService;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;
    
    private static final Logger log = LoggerFactory.getLogger(PatientController.class);
        
    /*GET a list of patients*/
    @GetMapping(value = "/patient/list")
	public ModelAndView getAllPatients() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("patientList", patientService.findAllPatients());
	    modelAndView.setViewName("list");
	    log.info("Get the list of patients!");
	    return modelAndView;
	}
	
    
    @GetMapping(value = "/patient/add")
	public ModelAndView addPatient() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("patientAdd", new Patient());
	    modelAndView.setViewName("add");
	    log.info("Add patients!");
	    return modelAndView;
	}
    
    @PostMapping(value = "/patient/validate")
    public ModelAndView validate(@Valid Patient patient, BindingResult result, Model model) {
        ModelAndView mav = new ModelAndView();
        if (!result.hasErrors()) {
            patientService.createPatient(patient);
            model.addAttribute("patientList", patientService.findAllPatients());
            mav.setViewName("redirect:/patient/list");
            log.info("Add Patient " + patient.toString());
            return mav;
        }
        mav.setViewName("patient/add");
        return mav;
    }

    /*@GetMapping(value = "/patient/add")
	public ModelAndView addPatient() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("patient", new Patient());
	    modelAndView.setViewName("list");
	    log.info("Add the patient to list!");
	    return modelAndView;
	}*/
}
