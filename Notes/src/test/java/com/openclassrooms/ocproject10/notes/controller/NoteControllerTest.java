package com.openclassrooms.ocproject10.notes.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.ocproject10.domain.Patient;
import com.openclassrooms.ocproject10.notes.domain.Note;
import com.openclassrooms.ocproject10.notes.repository.NoteRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class NoteControllerTest {

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
	private NoteRepository noteRepository;
    
	// Putted static because of repeating @Before of createPatient() method on tests
	static private Patient patient;
	
	// Putted static because of repeating @Before of createPatient() method on tests
	static private Note note;
	
	@Before
	public void createPatient() {
			mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
			
			patient = new Patient();
			patient.setId(1);
			patient.setGivenName("Test_1");
			patient.setFamilyName("TestNone");
			patient.setDob(LocalDate.of(Integer.parseInt("1966"), Integer.parseInt("12"), Integer.parseInt("31")));
			patient.setSex("Female");
			patient.setAddress("Brookside St");
			patient.setPhone("100-222-3333");

			note = new Note();
			note.setId("123");
			note.setNoteText("Testing note text");
			note.setPatientId("100");	
	}
	
    @Test
    public void getNoteList_statusIsSuccessful() throws Exception {
        mockMvc.perform(get("/noteList/" + 1))
                .andExpect(status().is2xxSuccessful());
    }




}
