package com.openclassrooms.ocproject10.patients.service;

import java.util.List;

import com.openclassrooms.ocproject10.patients.domain.Patient;

public interface PatientService {

	public List<Patient> findAllPatients();

	Patient createPatient(Patient patient);

}
