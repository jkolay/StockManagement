/**
 * 
 */
package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.StockNotFoundException;
import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.model.PriceRequest;
import com.stock.rest.webservices.model.StockRequest;
import com.stock.rest.webservices.model.response.StockResponse;
import com.stock.rest.webservices.model.entity.Price;
import com.stock.rest.webservices.model.entity.Stock;
import com.stock.rest.webservices.repository.PriceRepository;
import com.stock.rest.webservices.repository.StockRepository;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation class for Stock operation service 
 * @author jayati
 *
 */
@Service
public class StockOperationsServiceImpl implements StockOperationsService {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StockOperationsServiceImpl.class);
	private static final String INFO_FETCHALL="searched id(S):{} and name :{} from db";
	private static final String INFO_ADD="Saved Item id:{} and name :{} into db";

	private static final String INFO_UPDATE="Updated Item id:{} and name :{} into db";
	
	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private PriceRepository priceRepository;

	/**
	 *find  stocks wrt IDs
	 */
	@Override
	public StockResponse findStock(int stockId) {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new StockNotFoundException(String.format("Stock %s does not exists", stockId)));
		return new StockResponse(stock.getId(), stock.getName(), stock.getPrices(),stock.getNumberOfStocks());
	}

	/**
	 *Find all stocks
	 */
	@Override
	public List<StockResponse> findAllStocks() {
		List<StockResponse> stockResponse = new ArrayList<>();
		stockRepository.findAll().forEach(stock -> {
			stockResponse.add(new StockResponse(stock.getId(), stock.getName(),stock.getPrices(),stock.getNumberOfStocks()));
			LOGGER.info(INFO_FETCHALL,stock.getId(),stock.getName());
		});
		
		return stockResponse;
	}

	/**
	 *register new stock
	 */
	@Override
	public Stock addStock(StockRequest stockRequest) throws StockOperationsExceptions {

		PriceRequest priceRequest = stockRequest.getPrices() ;
		Price price =new Price();
		price.setTimestamp(new Timestamp(new DateTime().getMillis()));
		price.setPrices(priceRequest.getPrice());


		Stock stock=new Stock();
		stock.setId(stockRequest.getId());
		stock.setName(stockRequest.getName());
		stock.setNumberOfStocks(stockRequest.getNumberOfStocks());
		stock.setPrices(price);


		price.setStock(stock);

		Stock savedStock = stockRepository.save(stock);
		LOGGER.info(INFO_ADD,savedStock.getId(),savedStock.getName());
		return savedStock;
	}

	/**
	 *update latest price to the stock
	 */
	@Override
	public void updateStockPrice(int stockId, double newPrice) {
		Stock stock = stockRepository.findById(stockId)
				.orElseThrow(() -> new StockNotFoundException(String.format("Stock %s does not exists", stockId)));
		Price price = stock.getPrices();
		price.setPrices(newPrice);
		price.setTimestamp(new Timestamp(new DateTime().getMillis()));
		price.setStock(stock);
		LOGGER.info(INFO_UPDATE,stock.getId(),stock.getName());
		priceRepository.save(price);
	}


}