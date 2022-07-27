package com.david.retobackend.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.david.retobackend.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

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
	OrderService orderService;

	@Test
	public void get_allOrders_returnsOkWithListOfOrders() throws Exception {

		List<Order> orderList = new ArrayList<>();
		Order order1 = new Order("Venca");
		Order order2 = new Order("Amazon");
		orderList.add(order1);
		orderList.add(order2);

		// Mocking out the vehicle service
		Mockito.when(orderService.findAll()).thenReturn(orderList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].origin", is("Venca"))).andExpect(jsonPath("$[1].origin", is("Amazon")));
	}

	@Test
	public void post_createsNewOrder_andReturnsObjWith201() throws Exception {
		Order order = new Order("Carrefour");

		Mockito.when(orderService.save(Mockito.any(Order.class))).thenReturn(order);

		// Build post request with vehicle object payload
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(order));

		mockMvc.perform(builder).andExpect(status().isCreated()).andExpect(jsonPath("$.origin", is("Carrefour")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(order)));
	}
	
	
	@Test
	public void put_updatesOrder_andReturnsObjWith201() throws Exception {
		Order order = new Order("Carrefour");

		Mockito.when(orderService.save(Mockito.any(Order.class))).thenReturn(order);

		// Build post request with vehicle object payload
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8").content(this.mapper.writeValueAsBytes(order));

		mockMvc.perform(builder).andExpect(status().isAccepted()).andExpect(jsonPath("$.origin", is("Carrefour")))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(order)));
	}

	@Test
	public void delete_deleteOrder_Returns204Status() throws Exception {

		OrderService serviceSpy = Mockito.spy(orderService);
		Mockito.doNothing().when(serviceSpy).deleteById(1);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(orderService, times(1)).deleteById(1);
	}

	@Test
	public void get_Order_returnsOkWithOrder() throws Exception {

		Order order1 = new Order("Venca");

		// Mocking out the vehicle service
		Mockito.when(orderService.findById(0)).thenReturn(Optional.of(order1));

		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/0").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.origin", is("Venca")));
	}

}
