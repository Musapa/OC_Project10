package com.openclassrooms.ocproject10.reports.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.openclassrooms.ocproject10.domain.Report;
import com.openclassrooms.ocproject10.reports.controller.ReportController;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Bean
	public RestTemplate restTemplateReport() {
		return new RestTemplate();
	}

	@Autowired
	private Environment env;

	@Override
	public List<Patient> getAllPatients() {
		ResponseEntity<List<Patient>> response = restTemplateReport().exchange(getPatientsUrl() + "api/patient/list",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Patient>>() {
				});
		return response.getBody();
	}
	
	@Override
    public Patient findPatientInListOfPatient(int patientId) {
        List<Patient> patientsList = getAllPatients();
        for (Patient patient : patientsList) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

	private String getPatientsUrl() {
		String url = env.getProperty("PATIENTS_URL");
		if (url == null) {
			url = ReportController.PATIENTSURL;
		}
		return url;
	}

	@Override
	public List<Note> findAllNotesByPatientId(int patientId) {
		ResponseEntity<List<Note>> response = restTemplateReport().exchange(
				getNotesUrl() + "api/note/list/" + patientId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Note>>() {
				});
		return response.getBody();
	}

	private String getNotesUrl() {
		String url = env.getProperty("NOTES_URL");
		if (url == null) {
			url = ReportController.NOTESURL;
		}
		return url;
	}
	
	private List<String> triggerTerms = Arrays.asList("hemoglobina1c", "microalbumin", "bodyheight", "bodyweight",
			"smoker", "abnormal", "cholesterol", "dizziness", "relapse", "reaction", "antibodies");
	
	@Override
	public int getNumberOfTriggerTermsOnNoteList(List<Note> noteList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Report getReports(int patientId) {
		Patient patient = findPatientInListOfPatient(patientId);
		List<Note> noteList = findAllNotesByPatientId(patientId);
		int appearances = getNumberOfTriggerTermsOnNoteList(noteList);
		String riskLevel;
		int age = getAge(patient.getDob());
		String sex = patient.getSex();

		if (appearances < 2) {
			riskLevel = "None";
		} else if (appearances >= 2 && appearances < 6 && age >= 30) {
			riskLevel = "Borderline";
		} else if (appearances == 3 && age < 30 && sex.equals("Male")) {
			riskLevel = "In danger";
		} else if (appearances == 4 && age < 30 && sex.equals("Female")) {
			riskLevel = "In danger";
		} else if (appearances >= 6 && appearances < 8 && age >= 30) {
			riskLevel = "In danger";
		} else if (appearances == 5 && age < 30 && sex.equals("Male")) {
			riskLevel = "Early Onset";
		} else if (appearances == 6 && age < 30) {
            riskLevel = "Early Onset";
		} else if (appearances == 7 && age < 30 && sex.equals("Female")) {
			riskLevel = "Early Onset";
		} else if (appearances >= 8 && age >= 30 && age < 30) {
			riskLevel = "Early Onset";
		} else {
			riskLevel = "No risk level";
		}

		Report report = new Report();
		report.setAge(age);
		report.setPatientId(patientId);
		report.setRiskLevel(riskLevel);
		return report;
	}

	@Override
	public int getAge(LocalDate dob) {
		LocalDate timeNow = LocalDate.now();
		return Period.between(dob, timeNow).getYears();
	}
}
