package com.openclassrooms.ocproject10.patients.service;

import java.util.List;
import com.openclassrooms.ocproject10.domain.Patient;

public interface PatientService {

	public List<Patient> findAllPatients();

	Patient findPatientById(Integer id);
	
	Patient createPatient(Patient patient);
	
	void updatePatient(Patient patient);

	void deletePatientById(Integer id);

}
