package com.openclassrooms.ocproject10.patients.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.repository.PatientRepository;

@Service("patientService")
public class PatientServiceImpl implements PatientService {

	@Autowired
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

	// Create a patient
	@Override
	public Patient createPatient(Patient patient) {
		return patientRepository.save(patient);
	}

	// Update a patient
	@Override
	public void updatePatient(Patient patient) {
		Optional<Patient> patientOptional = patientRepository.findById(patient.getId());
		if (patientOptional.isPresent()) {
			patientRepository.save(patient);
		}
	}

	// Delete a patient
	@Override
	public void deletePatientById(Integer id) {
		Optional<Patient> patientOptional = patientRepository.findById(id);
		if (patientOptional.isPresent()) {
			patientRepository.delete(patientOptional.get());
		}
	}
}
