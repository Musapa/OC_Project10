package com.openclassrooms.ocproject10.patients.controller;

import static org.junit.Assert.assertEquals;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ocproject10.patients.PatientsApplication;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.patients.repository.PatientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PatientsApplication.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PatientControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Autowired
	private PatientRepository patientRepository;

	// Putted static because of repeating @Before of createPatient() method on tests
	static private Patient patient;

	@Before
	public void createPatient() {
		if (patient == null) {

			patient = new Patient();

			Patient patient = new Patient();
			patient.setGivenName("Test_1");
			patient.setFamilyName("TestNone");
			patient.setDob(LocalDate.of(Integer.parseInt("1966"), Integer.parseInt("12"), Integer.parseInt("31")));
			patient.setSex("Female");
			patient.setAddress("Brookside St");
			patient.setPhone("100-222-3333");

			patient = patientRepository.save(patient);

			Patient patient2 = new Patient();
			patient2.setGivenName("Test_2");
			patient2.setFamilyName("TestBorderline");
			patient2.setDob(LocalDate.of(Integer.parseInt("1945"), Integer.parseInt("06"), Integer.parseInt("24")));
			patient2.setSex("Male");
			patient2.setAddress("High St");
			patient2.setPhone("200-333-4444");

			patient2 = patientRepository.save(patient2);
		}
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
	public void addPatientSaveValid() throws Exception {
		mockMvc.perform(post("/patient/save").param("givenName", "Test_3").param("familyName", "TestInDanger")
				.param("dob", "2002-06-28").param("sex", "Male").param("address", "Club Road")
				.param("phone", "300-444-5555")).andExpect(view().name("redirect:/patient/list")).andExpect(status().is3xxRedirection())
				.andExpect(model().errorCount(0)).andReturn();
		
		mockMvc.perform(get("/patient/delete/3")).andExpect(view().name("redirect:/patient/list"))
		.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void addPatientSaveInvalid() throws Exception {
		// invalid date
		mockMvc.perform(post("/patient/save").param("givenName", "Test_3").param("familyName", "TestInDanger")
				.param("dob", "invalid date").param("sex", "Male").param("address", "Club Road")
				.param("phone", "300-444-5555")).andExpect(view().name("patient/add")).andExpect(status().isOk())
				.andExpect(model().errorCount(1)).andReturn();
	}

	@Test
	public void updatePatientForm() throws Exception {
		MvcResult result = mockMvc.perform(get("/patient/update/2")).andExpect(view().name("patient/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
	}

	@Test
	public void updatePatientValid() throws Exception {
		mockMvc.perform(post("/patient/update/2").param("givenName", "Test_2").param("familyName", "TestEarlyOnset")
				.param("dob", "2002-06-28").param("sex", "Female").param("address", " Valley Dr")
				.param("phone", "400-555-6666")).andExpect(view().name("redirect:/patient/list"))
				.andExpect(status().is3xxRedirection()).andExpect(model().errorCount(0)).andReturn();
	}

	@Test
	public void updatePatientInvalid() throws Exception {
		// invalid gender
		mockMvc.perform(post("/patient/update/2").param("givenName", "Test_2").param("familyName", "TestEarlyOnset")
				.param("dob", "2002-06-28").param("sex", "Invalid gender").param("address", " Valley Dr")
				.param("phone", "400-555-6666")).andExpect(view().name("patient/update")).andExpect(status().isOk())
				.andExpect(model().errorCount(1)).andReturn();
	}

	@Test
	public void deletePatient() throws Exception {
		mockMvc.perform(get("/patient/delete/1")).andExpect(view().name("redirect:/patient/list"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void apiGetAllPatients() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/patient/list")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		List<Patient> patients = objectMapper.readValue(content, new TypeReference<List<Patient>>() {
		});
		assertEquals("There should be 1 patients because Test_1 patient is deleted", 1, patients.size());
	}


}
