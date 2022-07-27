package com.david.retobackend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.david.retobackend.model.Order;
import com.david.retobackend.model.Truck;
import com.david.retobackend.service.OrderService;
import com.david.retobackend.service.TruckService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TruckControllerTest {

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
	OrderService orderService;

	@Test
	public void get_allTrucks_returnsOkWithListOfTrucks() throws Exception {

		List<Truck> truckList = new ArrayList<>();
		Truck truck1 = new Truck("Audi", "Q3");
		Truck truck2 = new Truck("Audi", "Q5");
		truckList.add(truck1);
		truckList.add(truck2);

		// Mocking out the vehicle service
		Mockito.when(truckService.findAll()).thenReturn(truckList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/trucks").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].brand", is("Audi"))).andExpect(jsonPath("$[1].brand", is("Audi")))
				.andExpect(jsonPath("$[0].model", is("Q3"))).andExpect(jsonPath("$[1].model", is("Q5")));
	}

	@Test
	public void post_createsNewTruck_andReturnsObjWith201() throws Exception {
		Truck truck = new Truck("Audi", "Q3");
		Order order = new Order("Amazon");

		Mockito.when(truckService.findById(0)).thenReturn(Optional.of(truck));
		Mockito.when(orderService.findById(0)).thenReturn(Optional.of(order));

		HttpHeaders headers = new HttpHeaders();
		headers.set("order_id", "0");
		headers.set("truck_id", "0");

		// Build post request with vehicle object payload

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/orderToTruck")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(truck)).headers(headers);

		mockMvc.perform(builder).andExpect(status().isOk()).andExpect(jsonPath("$.brand", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q3")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(truck)));
	}

	@Test
	public void post_addOrderToTruck_andReturnsObjWith200() throws Exception {
		Truck truck = new Truck("Audi", "Q3");

		Mockito.when(truckService.save(Mockito.any(Truck.class))).thenReturn(truck);

		// Build post request with vehicle object payload
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/trucks")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(truck));

		mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.brand", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q3")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(truck)));
	}

	@Test
	public void put_updatesOrder_andReturnsObjWith201() throws Exception {
		Truck truck = new Truck("Audi", "Q3");

		Mockito.when(truckService.save(Mockito.any(Truck.class))).thenReturn(truck);

		// Build post request with vehicle object payload
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/trucks")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(truck));

		mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.brand", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q3")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(truck)));
	}

	@Test
	public void delete_deleteTruck_Returns204Status() throws Exception {

		TruckService serviceSpy = Mockito.spy(truckService);
		Mockito.doNothing().when(serviceSpy).deleteById(1);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trucks/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(truckService, times(1)).deleteById(1);
	}

	@Test
	public void get_Truck_returnsOkWithTruck() throws Exception {

		Truck truck = new Truck("Audi", "Q3");

		// Mocking out the vehicle service
		Mockito.when(truckService.findById(0)).thenReturn(Optional.of(truck));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/trucks/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.brand", is("Audi")))
				.andExpect(jsonPath("$.model", is("Q3")));
	}

}
