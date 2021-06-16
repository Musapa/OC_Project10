package com.openclassrooms.ocproject10.patients.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
	    return modelAndView;
	}
	
	
}
