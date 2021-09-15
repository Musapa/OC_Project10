package com.openclassrooms.ocproject10.notes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class NoteControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private NoteRepository noteRepository;
	
	private MockRestServiceServer mockServer;

	static private final String PATIENTID = "1";
	static private final String PATIENTID2 = "2";

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}
	
	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Before
	public void createPatientNotes() {

		Note note = new Note();
		note.setId("11");
		note.setNoteText("Testing note text");
		note.setPatientId(PATIENTID);
		note = noteRepository.save(note);

		Note note2 = new Note();
		note2.setId("13");
		note2.setNoteText("Testing note text 13");
		note2.setPatientId(PATIENTID2);
		note2 = noteRepository.save(note2);
	}

	@Test
	public void getAllNotesByPatientId() throws Exception {

		MvcResult result = mockMvc.perform(get("/note/list/" + 1)).andExpect(status().is2xxSuccessful()).andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println("Get All notes content: " + content);

		int foundNote11 = content.indexOf("11");
		assertNotEquals("Cannot find note", foundNote11, -1);
	}

	@Test
	public void addNoteForm() throws Exception {
		mockMvc.perform(get("/note/add/1")).andExpect(view().name("note/add")).andExpect(model().errorCount(0))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void addPatientNoteSaveValid() throws Exception {
		mockMvc.perform(
				post("/note/save/1").param("id", "12").param("patientId", "1").param("noteText", "TestInDanger"))
				.andExpect(view().name("redirect:/note/list/{patientId}")).andExpect(status().is3xxRedirection())
				.andExpect(model().errorCount(0)).andReturn();

		mockMvc.perform(get("/note/delete/1/12")).andExpect(view().name("redirect:/note/list/{patientId}"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void addPatientNoteSaveInvalidNoteText() throws Exception {
		// empty noteText
		mockMvc.perform(post("/note/save/1").param("id", "12").param("patientId", "1").param("noteText", ""))
				.andExpect(view().name("note/add")).andExpect(status().isOk()).andExpect(model().errorCount(1))
				.andReturn();
	}

	@Test
	public void addPatientNoteSaveInvalidPatientId() throws Exception {
		// empty patientId
		mockMvc.perform(post("/note/save/1").param("id", "12").param("patientId", "").param("noteText", "TestInDanger"))
				.andExpect(view().name("note/add")).andExpect(status().isOk()).andExpect(model().errorCount(1))
				.andReturn();
	}

	@Test
	public void updatePatientNoteForm() throws Exception {
		mockMvc.perform(get("/note/update/1/11")).andExpect(view().name("note/update")).andExpect(model().errorCount(0))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void updatePatientNoteValid() throws Exception {
		mockMvc.perform(
				post("/note/update/1/11").param("patientId", "1").param("id", "11").param("noteText", "UpdatedText"))
				.andExpect(view().name("redirect:/note/list/{patientId}")).andExpect(status().is3xxRedirection())
				.andExpect(model().errorCount(0)).andReturn();
	}

	@Test
	public void updatePatientNoteInvalidNoteText() throws Exception {
		// empty noteText
		mockMvc.perform(post("/note/update/1/11").param("patientId", "1").param("id", "11").param("noteText", ""))
				.andExpect(view().name("note/update")).andExpect(status().isOk()).andExpect(model().errorCount(1))
				.andReturn();
	}

	@Test
	public void updatePatientNoteInvalidPatientId() throws Exception {
		// empty patientId
		mockMvc.perform(
				post("/note/update/1/11").param("patientId", "").param("id", "11").param("noteText", "Some Text"))
				.andExpect(view().name("note/update")).andExpect(status().isOk()).andExpect(model().errorCount(1))
				.andReturn();
	}

	@Test
	public void deleteNote() throws Exception {
		mockMvc.perform(get("/note/delete/1/13")).andExpect(view().name("redirect:/note/list/{patientId}"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}

	@Test
	public void showViewForm() throws Exception {
		mockMvc.perform(get("/note/view/1/11")).andExpect(view().name("note/view")).andExpect(model().errorCount(0))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void apiGetAllPatientNotes() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/note/list/2")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		List<Note> notes = objectMapper.readValue(content, new TypeReference<List<Note>>() {
		});
		assertEquals("There should be 1 note with id=13 for patient 2.", 1, notes.size());
	}

	@Test
	public void getAllPatients() throws Exception {
		Patient patient = new Patient();
		String familyName = "Ferguson";
		List<Patient> patients = new ArrayList<>();
		
		patient.setFamilyName(familyName);
		patients.add(patient);
		
		String inputJson = objectMapper.writeValueAsString(patients);
		
		mockServer.expect(ExpectedCount.twice(),requestTo(new URI(NoteController.PATIENTSURL + "api/patient/list"))).andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(inputJson));

		MvcResult result = mockMvc.perform(get("/patient/list")).andExpect(view().name("patient/patientList"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
		
		mockServer.verify();

		String content = result.getResponse().getContentAsString();
		System.out.println("Content" + content);
		
		int foundPatient = content.indexOf("Ferguson");
		assertNotEquals("Cannot find patient", foundPatient, -1);
	}
}
