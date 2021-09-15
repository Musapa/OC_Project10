package com.openclassrooms.ocproject10.reports.controller;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.domain.Report;
import com.openclassrooms.ocproject10.reports.ReportsApplication;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReportsApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReportControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	static private final String PATIENTID = "1";
	
	@Before
	public void createPatientNotes() {
		Note note = new Note();
		note.setId("11");
		note.setNoteText("Testing note text");
		note.setPatientId(PATIENTID);
	}
	
	@Before
	public void createPatientReport() {
		Report report = new Report();
		report.setAge(33);
		report.setRiskLevel("Early Onset");
	}
	
	@Test
	public void getAllPatients() throws Exception {
		Patient patient = new Patient();
		String familyName = "Ferguson";
		List<Patient> patients = new ArrayList<>();
		
		patient.setFamilyName(familyName);
		patients.add(patient);
		
		String inputJson = objectMapper.writeValueAsString(patients);
		
		mockServer.expect(ExpectedCount.twice(),requestTo(new URI(ReportController.PATIENTSURL + "api/patient/list"))).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(inputJson));

		MvcResult result = mockMvc.perform(get("/patient/list")).andExpect(view().name("patient/patientList"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
		
		mockServer.verify();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
		
		int foundPatient = content.indexOf("Ferguson");
		assertNotEquals("Cannot find patient", foundPatient, -1);
	}
	
	@Test
	public void getAllNotesByPatientId() throws Exception {
		Note note = new Note();
		String noteText = "Test Note Text";
		List<Note> notes = new ArrayList<>();
		
		note.setNoteText(noteText);
		notes.add(note);
		
		String inputJson = objectMapper.writeValueAsString(notes);
		
		mockServer.expect(ExpectedCount.twice(),requestTo(new URI(ReportController.NOTESURL + "api/note/list/" + PATIENTID))).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(inputJson));

		MvcResult result = mockMvc.perform(get("/note/list/1")).andExpect(view().name("note/noteList"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
		
		mockServer.verify();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
		
		int foundNote = content.indexOf("Test Note Text");
		assertNotEquals("Cannot find note", foundNote, -1);
	}
	

}
