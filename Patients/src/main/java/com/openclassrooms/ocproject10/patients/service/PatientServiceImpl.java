package com.openclassrooms.ocproject10.patients.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.repository.PatientRepository;

@Service("patientService")
public class PatientServiceImpl implements PatientService{

	@Autowired
	private PatientRepository patientRepository;
	

	@Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
}
