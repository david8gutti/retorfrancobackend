package com.david.retobackend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.david.retobackend.model.Location;
import com.david.retobackend.model.Truck;
import com.david.retobackend.service.LocationService;
import com.david.retobackend.service.TruckService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

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
	TruckService truckService;

	@MockBean
	LocationService locationService;

	@Test
	public void post_addLocationToTruck_andReturnsObjWith200() throws Exception {
		Truck truck = new Truck("Audi", "Q3");
		Location location = new Location(2.222, 3.333);

		Mockito.when(truckService.findById(0)).thenReturn(Optional.of(truck));
		Mockito.when(locationService.findById(0)).thenReturn(Optional.of(location));

		HttpHeaders headers = new HttpHeaders();
		headers.set("truck_id", "0");

		// Build post request with vehicle object payload

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/locationToTruck")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(truck)).headers(headers);

		mockMvc.perform(builder).andExpect(status().isOk()).andExpect(jsonPath("$.brand", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q3")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(truck)));
	}

	@Test
	public void get_Location_returnsOkWithLocation() throws Exception {

		Location location = new Location(2.222, 3.333);

		// Mocking out the vehicle service
		Mockito.when(locationService.findById(0)).thenReturn(Optional.of(location));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/locations/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.north", is(2.222)))
				.andExpect(jsonPath("$.west", is(3.333)));
	}

}
