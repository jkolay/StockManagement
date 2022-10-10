package com.stock.rest.webservices.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.stock.rest.webservices.model.entity.Price;
import com.stock.rest.webservices.model.response.StockResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.stock.rest.webservices.controller.AdminStockController;
import com.stock.rest.webservices.exception.StockNotFoundException;

import com.stock.rest.webservices.services.StockOperationsServiceImpl;

@RunWith(SpringRunner.class)
public class StockControllerUnitTest {

	private MockMvc mockMvc;

	@Mock
	private StockOperationsServiceImpl stockOperationsServiceImpl;

	@InjectMocks
	private AdminStockController stockController;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
	}

	@Test
	public void givenNoExistingStocksWhenGetAllStocksThenRespondWithStatusOkAndEmptyArray() throws Exception {
		StockResponse stockResponse = new StockResponse(1, "testStcok", null,null);
		List<StockResponse> stockList = new ArrayList<>();
		stockList.add(stockResponse);
		when(stockOperationsServiceImpl.findAllStocks()).thenReturn(stockList);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/stocks/retrieveAll").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.listOfStock", hasSize(1))).andExpect(jsonPath("$.listOfStock[0].id", is(1)))
				.andExpect(jsonPath("$.listOfStock[0].name", is("testStcok")));
	}

	@Test
	public void testStockListById() throws Exception {
		StockResponse stockResponse = new StockResponse(1, "testStock", new Price(),null);
		when(stockOperationsServiceImpl.findStock(1)).thenReturn(stockResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/stocks/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("testStock")));
	}


	@Test
	public void testUpdatePriceForStock() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/stocks/update/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" + "	\"price\":400\n" + "}").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void testGetStockPriceWithInvalidId() {
		try {
			when(stockOperationsServiceImpl.findStock(8768787))
					.thenThrow(new StockNotFoundException("Stock 8768787 does not exists"));
			mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/stocks/8768787").accept(MediaType.APPLICATION_JSON))
					.andDo(print()).andExpect(status().isNotFound());
		} catch (Exception e) {
			fail(e.toString());
		}
	}
}