package com.openclassrooms.ocproject10.reports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.reports.controller.ReportController;

@Service("reportService")
public class ReportService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment env;
	
	public List<Patient> getAllPatients() {
		ResponseEntity<List<Patient>> response = restTemplate
				.exchange(getPatientsUrl() + "patient/list", HttpMethod.GET, null,new ParameterizedTypeReference<List<Patient>>() {});
		return response.getBody();
	}
	
	public List<Patient> getAllPatientsApi() {
		ResponseEntity<List<Patient>> response = restTemplate
				.exchange(getPatientsUrl() + "api/patient/list", HttpMethod.GET, null,new ParameterizedTypeReference<List<Patient>>() {});
		return response.getBody();
	}
	
	private String getPatientsUrl() {
		String url = env.getProperty("PATIENTS_URL");
		if (url == null) {
			url = ReportController.PATIENTSURL;
		}
		return url;
	}
	
}
