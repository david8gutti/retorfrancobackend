package com.david.retobackend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.david.retobackend.model.History;
import com.david.retobackend.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class HistoryControllerTest {

	@Autowired
	MockMvc mockMvc;

	/*
	 * Jackson mapper for Object -> JSON conversion
	 */
	@Autowired
	ObjectMapper mapper;

	/*
	 * We use @MockBean because the WebApplicationContext does not provide
	 * any @Component, @Service or @Repository beans instance/bean of this service
	 * in its context. It only loads the beans solely required for testing the
	 * controller.
	 */
	@MockBean
	HistoryService historyService;

	
	@Test
	public void get_allTrucks_returnsOkWithListOfTrucks() throws Exception {

		List<History> history = new ArrayList<>();
		
		SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date(System.currentTimeMillis());
		String dateTime= formatter.format(date);
		
		History history1 = new History(dateTime);
		History history2 = new History(dateTime);
		history.add(history1);
		history.add(history2);

		// Mocking out the vehicle service
		Mockito.when(historyService.findAll()).thenReturn(history);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/history").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].date", is(dateTime)))
				.andExpect(jsonPath("$[0].date", is(dateTime)));
	}

	

}
