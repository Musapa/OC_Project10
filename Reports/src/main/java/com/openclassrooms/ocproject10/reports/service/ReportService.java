package com.openclassrooms.ocproject10.reports.service;

import java.time.LocalDate;
import java.util.List;

import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.domain.Report;

public interface ReportService {

	List<Patient> getAllPatients();
	
	Patient findPatientInListOfPatient(int patientId);

	List<Note> findAllNotesByPatientId(int patientId);
	
	int getNumberOfTriggerTermsOnNoteList(int patientId);
	
	Report getReports(int patientId);

	int getAge(LocalDate dob);

}
