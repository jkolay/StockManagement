package com.stock.rest.webservices.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stock.rest.webservices.model.entity.Price;
import com.stock.rest.webservices.model.entity.Stock;
import com.stock.rest.webservices.model.response.StockResponse;
import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.stock.rest.webservices.exception.StockNotFoundException;

import com.stock.rest.webservices.repository.PriceRepository;
import com.stock.rest.webservices.repository.StockRepository;

@RunWith(MockitoJUnitRunner.class)
public class StockOperationServiceImplTest {
	
	@InjectMocks
	private StockOperationsServiceImpl testStockOperationServiceImpl;
	
	@Mock
	private StockRepository stockRepository;
	
	@Mock
	private PriceRepository priceRepository;

	@Test
	public void testFindStock() {
		Stock stock = new Stock(1, "testStock",null,10);
		when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
		StockResponse stockResponse = testStockOperationServiceImpl.findStock(1);
		assertEquals("Check Stock Name", "testStock", stockResponse.getName());
		assertEquals("Check Stock ID", new BigDecimal(1), new BigDecimal(stockResponse.getId()));	
		verify(stockRepository).findById(1);
		verifyNoMoreInteractions(stockRepository);
	}
	
	@Test(expected = StockNotFoundException.class)
	public void testFindStockNotFoundException() {
			testStockOperationServiceImpl.findStock(1);	
	}
	
	@Test
	public void testAllFindStocks() {
		Stock stock = new Stock(1, "testStock",null,11);
		List<Stock> responses = new ArrayList<Stock>();
		responses.add(stock);
		when(stockRepository.findAll()).thenReturn(responses);
		List<StockResponse> stockResponse = testStockOperationServiceImpl.findAllStocks();
		assertEquals("Size of the list of responbse", stockResponse.size(), 1);
		assertEquals("Check name of the stock", "testStock", stockResponse.get(0).getName());
		assertEquals("Check Stock ID", new BigDecimal(1), new BigDecimal(stockResponse.get(0).getId()));	
		verify(stockRepository).findAll();
		verifyNoMoreInteractions(stockRepository);
	}
	
	@Test
	public void testaddStock() {
		Stock stock = new Stock(1, "testStock",null,11);
		Price price = new Price(1, 22.0, null);

		stock.setPrices(price);
		when(stockRepository.save(stock)).thenReturn((stock));
		Stock stockResponse = stockRepository.save(stock);
		assertEquals("Check Stock Name", "testStock", stockResponse.getName());
		assertEquals("Check Stock ID", new BigDecimal(1), new BigDecimal(stockResponse.getId()));	
		assertEquals("List size of the price", stockResponse.getPrices(), price);
		assertEquals("Price details", stockResponse.getPrices().getPrices(), new Double(22.0));
		assertEquals("Price Id", new BigDecimal(1), new BigDecimal(stockResponse.getPrices().getId()));
		verify(stockRepository).save(stock);
		verifyNoMoreInteractions(stockRepository);
	}

	@Test
	public void testUpdateStockPrice() {
		Stock stock = new Stock(1, "testStock",null,6);
		DateTimeUtils.setCurrentMillisFixed(10L);
		Price price = new Price(1, 22.0, new Timestamp(10L));
		price.setStock(stock);
		stock.setPrices(price);
		priceRepository.save(price);
		stockRepository.save(stock);
		when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
		testStockOperationServiceImpl.updateStockPrice(1, 25.0);
		verify(stockRepository).findById(1);
	}
	
	@Test(expected = StockNotFoundException.class)
	public void testUpdateStockPriceWithStockNotFoundException() {
		Stock stock = new Stock(1, "testStock",null,15);
		DateTimeUtils.setCurrentMillisFixed(10L);
		Price price = new Price(1, 22.0, new Timestamp(10L));
		price.setStock(stock);
		testStockOperationServiceImpl.updateStockPrice(1, 22.0);
	}
}
