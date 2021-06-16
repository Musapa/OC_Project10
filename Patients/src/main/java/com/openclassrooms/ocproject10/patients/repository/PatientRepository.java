package com.openclassrooms.ocproject10.patients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.ocproject10.patients.domain.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	List<Patient> findAll();

	Optional<Patient> findById(Integer id);
}
