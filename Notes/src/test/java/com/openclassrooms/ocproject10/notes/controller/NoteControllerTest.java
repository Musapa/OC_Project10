package com.openclassrooms.ocproject10.notes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ocproject10.domain.Note;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.NotesApplication;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotesApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class NoteControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private NoteRepository noteRepository;

	static private Patient patient;

	@Before
	public void setupMockmvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	}

	@Before
	public void createPatientNotes() {

		patient = new Patient();
		patient.setId(1);
		patient.setGivenName("Test_1");
		patient.setFamilyName("TestNone");
		patient.setDob(LocalDate.of(Integer.parseInt("1966"), Integer.parseInt("12"), Integer.parseInt("31")));
		patient.setSex("Female");
		patient.setAddress("Brookside St");
		patient.setPhone("100-222-3333");
		
		Note note = new Note();
		note.setId("11");
		note.setNoteText("Testing note text");
		note.setPatientId("1");
		note = noteRepository.save(note);
		
		Note note2 = new Note();
		note2.setId("13");
		note2.setNoteText("Testing note text 13");
		note2.setPatientId("2");
		note2 = noteRepository.save(note2);
	}

	@Test
	public void getAllNotesByPatientId() throws Exception {
		List<Patient> patientList = new ArrayList<>();
		patientList.add(patient);

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
	public void updatePatientForm() throws Exception {
		mockMvc.perform(get("/note/update/1/11")).andExpect(view().name("note/update"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void updatePatientValid() throws Exception {
		mockMvc.perform(post("/note/update/1/11").param("patientId", "1").param("id", "11").param("noteText", "UpdatedText"))
				.andExpect(view().name("redirect:/note/list/{patientId}")).andExpect(status().is3xxRedirection())
				.andExpect(model().errorCount(0)).andReturn();
	}


	@Test
	public void deleteNote() throws Exception {
		mockMvc.perform(get("/note/delete/1/13")).andExpect(view().name("redirect:/note/list/{patientId}"))
				.andExpect(model().errorCount(0)).andExpect(status().isFound());
	}
	
	@Test
	public void showViewForm() throws Exception {
		mockMvc.perform(get("/note/view/1/11")).andExpect(view().name("note/view"))
				.andExpect(model().errorCount(0)).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void apiGetAllPatientNotes() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/note/list/2")).andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		List<Note> notes = objectMapper.readValue(content, new TypeReference<List<Note>>() {
		});
		assertEquals("There should be 1 note with id=13 for patient 2.", 1, notes.size());
	}
}
