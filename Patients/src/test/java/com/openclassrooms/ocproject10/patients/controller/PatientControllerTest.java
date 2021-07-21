package com.openclassrooms.ocproject10.patients.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.openclassrooms.ocproject10.patients.PatientsApplication;
import com.openclassrooms.ocproject10.patients.domain.Patient;
import com.openclassrooms.ocproject10.patients.repository.PatientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatientsApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PatientControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Autowired
	private PatientRepository patientRepository;

	private Patient patient;

	@Before
	public void createPatient() {
		patient = new Patient();
		Patient patient = new Patient();
		patient.setGivenName("Test_1");
		patient.setFamilyName("TestFamily_1");
		patient.setDob(LocalDate.of(Integer.parseInt("1990"), Integer.parseInt("04"), Integer.parseInt("21")));
		patient.setSex("Male");
		patient.setAddress("TestAddress_1");
		patient.setPhone("033111111");

		patient = patientRepository.save(patient);
	}

	@Test
	public void getAllPatients() throws Exception {
		List<Patient> patientList = new ArrayList<>();
		patientList.add(patient);

		MvcResult result = mockMvc.perform(get("/patient/list")).andExpect(view().name("patient/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void addPatientForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/patient/add")).andExpect(view().name("patient/add"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void addPatientValidate() throws Exception {
		MvcResult result = mockMvc
				.perform(post("/patient/validate").param("id", "10").param("givenName", "Test_2")
						.param("familyName", "TestFamily_2").param("dob", "1990-04-21").param("sex", "Male")
						.param("address", "TestAddress_2").param("phone", "033222222"))
				.andExpect(view().name("redirect:/patient/list")).andExpect(status().is3xxRedirection())
				.andExpect(model().errorCount(0)).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

}
