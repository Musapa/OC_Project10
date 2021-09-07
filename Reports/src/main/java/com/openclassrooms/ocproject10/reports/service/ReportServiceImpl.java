package com.openclassrooms.ocproject10.reports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.reports.controller.ReportController;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	@Bean
	public RestTemplate restTemplateReport() {
	    return new RestTemplate();
	}
	
	@Autowired
	private Environment env;
	
	@Override
	public List<Patient> getAllPatients() {
		ResponseEntity<List<Patient>> response = restTemplateReport()
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

	public List<Note> findAllNotesByPatientId(String patientId) {
		ResponseEntity<List<Note>> response = restTemplateReport()
				.exchange(getNotesUrl() + "api/note/list/" + patientId, HttpMethod.GET, null,new ParameterizedTypeReference<List<Note>>() {});
		return response.getBody();
	}
	
	private String getNotesUrl() {
		String url = env.getProperty("NOTES_URL");
		if (url == null) {
			url = ReportController.NOTESURL;
		}
		return url;
	}
	
}
