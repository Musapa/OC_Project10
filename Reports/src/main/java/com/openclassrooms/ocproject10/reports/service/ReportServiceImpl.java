package com.openclassrooms.ocproject10.reports.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.repository.PatientRepository;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	private PatientRepository patientRepository;

	@Override
	public List<Patient> findAllPatients() {
		return patientRepository.findAll();
	}

	// Get a patient byId
	@Override
	public Patient findPatientById(Integer id) {
		Optional<Patient> patientOptional = patientRepository.findById(id);
		if (patientOptional.isPresent()) {
			Patient patient = patientOptional.get();
			return patient;
		}
		return null;
	}
	
}
