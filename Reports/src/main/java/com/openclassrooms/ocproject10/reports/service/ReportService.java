package com.openclassrooms.ocproject10.reports.service;

import java.util.List;

import com.openclassrooms.ocproject10.patients.domain.Patient;

public interface ReportService {

	public List<Patient> findAllPatients();

	Patient findPatientById(Integer id);
	
}
