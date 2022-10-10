package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.StockNotFoundException;
import com.stock.rest.webservices.exception.StockOperationsExceptions;
import com.stock.rest.webservices.exception.UserNotFoundException;
import com.stock.rest.webservices.model.BuyStockRequest;
import com.stock.rest.webservices.model.response.AllStockResponse;
import com.stock.rest.webservices.model.response.BuyStockResponse;
import com.stock.rest.webservices.model.SellStockRequest;
import com.stock.rest.webservices.model.response.SellStockResponse;
import com.stock.rest.webservices.model.entity.Stock;
import com.stock.rest.webservices.model.entity.User;
import com.stock.rest.webservices.model.entity.UserStockDetails;
import com.stock.rest.webservices.model.entity.Wallet;
import com.stock.rest.webservices.repository.StockRepository;
import com.stock.rest.webservices.repository.UserRepository;
import com.stock.rest.webservices.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStockOperationsServiceImpl implements UserStockOperationsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserStockRepository userStockRepository;
    @Override
    public BuyStockResponse buyNewStock(BuyStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions {
        Long userId=stockRequest.getUserId();
        int stockId=stockRequest.getStockId();
        int totalStockAfterBuy=0;
        Integer requestNumberOfStocks= stockRequest.getNumberOfStocks();
        User currentUser= userRepository.findById(userId)
                        .orElseThrow(()-> new UserNotFoundException(String.format("User id does not exists",stockRequest.getUserId())));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(String.format("Stock %s does not exists", stockId)));
        Integer currentStockNumbers= stock.getNumberOfStocks();
        if(currentStockNumbers>=requestNumberOfStocks){
            Wallet walletOfCurrentUser= currentUser.getWallet();
            Double currentWalletBalance= walletOfCurrentUser.getBalance();
            Double requiredAmount= requestNumberOfStocks *stock.getPrices().getPrices();
            if(currentWalletBalance>=requiredAmount){

                totalStockAfterBuy=processBuyOperation(userId, stockId, requestNumberOfStocks, currentUser, stock, currentStockNumbers, walletOfCurrentUser, currentWalletBalance, requiredAmount);

            } else{
                throw new StockOperationsExceptions("Please add sufficient balance to your account.Available balance "+currentWalletBalance);
            }

        }
        else{
            throw new StockOperationsExceptions("Stock numbers are limited. Currently available number is "+currentStockNumbers);
        }

       return BuyStockResponse.builder().stockId(stockId).numberOFStocks(totalStockAfterBuy).status("SUCCESS").build();
    }

    @Override
    public SellStockResponse sellStock(SellStockRequest stockRequest) throws UserNotFoundException, StockOperationsExceptions {
        Long userId=stockRequest.getUserId();
        int stockId=stockRequest.getStockId();
        int availableStockAfterSell=0;
        User currentUser= userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(String.format("User id does not exists",stockRequest.getUserId())));
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new StockNotFoundException(String.format("Stock %s does not exists", stockId)));
        UserStockDetails existingStockDetails= userStockRepository.findByUserIdAndStockId(userId,stockId);
        if(existingStockDetails!=null && existingStockDetails.getNumberOfStocks()>=stockRequest.getNumberOfStocks()){
            availableStockAfterSell = processSellOperation(currentUser,stock,existingStockDetails,stockRequest,userId,stockId);
        }
        else{
            throw new StockOperationsExceptions("You do not have sufficient stock.Please check your stock details ");

        }
        return SellStockResponse.builder().stockId(stockId).numberOFStocks(availableStockAfterSell).status("SUCCESS").build();
    }

    @Override
    public List<AllStockResponse> retrieveStocks(int userId) {
        List<Stock> listOfStocks= stockRepository.getStockForUser(userId);
       return listOfStocks.stream().map(stock->{
            UserStockDetails existingStockDetails= userStockRepository.findByUserIdAndStockId(Long.valueOf(userId),stock.getId());
            return AllStockResponse.builder().name(stock.getName()).id(stock.getId()).prices(stock.getPrices()).numberOfStocks(stock.getNumberOfStocks()).selfOwnedStock(existingStockDetails.getNumberOfStocks()).build();
        }).collect(Collectors.toList());
    }

    private int processSellOperation(User currentUser, Stock stock, UserStockDetails existingStockDetails, SellStockRequest stockRequest, Long userId, int stockId) {
        int stockNumberAfterSell= stock.getNumberOfStocks()+stockRequest.getNumberOfStocks();
        stock.setNumberOfStocks(stockNumberAfterSell);
        int numberOfSellingStock= stockRequest.getNumberOfStocks();
        double currentStockPrice= stock.getPrices().getPrices();
        Double amount = currentUser.getWallet().getBalance()+(currentStockPrice*numberOfSellingStock);
        currentUser.getWallet().setBalance(amount);
        userRepository.save(currentUser);

        int currentStockNumber= existingStockDetails.getNumberOfStocks()-numberOfSellingStock;
        existingStockDetails.setNumberOfStocks(currentStockNumber);
        userStockRepository.save(existingStockDetails);

        stockRepository.save(stock);
        return currentStockNumber;


    }

    private int processBuyOperation(Long userId, int stockId, Integer requestNumberOfStocks, User currentUser, Stock stock, Integer currentStockNumbers, Wallet walletOfCurrentUser, Double currentWalletBalance, Double requiredAmount) {
        UserStockDetails existingStockDetails= userStockRepository.findByUserIdAndStockId(userId,stockId);
        int totalStockAfterBuy=0;
        if(existingStockDetails!=null){
            int existingStockNumbers= existingStockDetails.getNumberOfStocks();
            totalStockAfterBuy= existingStockNumbers+requestNumberOfStocks;
            existingStockDetails.setNumberOfStocks(totalStockAfterBuy);
            userStockRepository.save(existingStockDetails);
        }
        else {
            UserStockDetails details= new UserStockDetails();
            details.setStockId(stockId);
            details.setUserId(userId);
            totalStockAfterBuy= requestNumberOfStocks;
            details.setNumberOfStocks(requestNumberOfStocks);
            userStockRepository.save(details);
        }
        Double balance = currentWalletBalance - requiredAmount;
        walletOfCurrentUser.setBalance(balance);
        currentUser.setWallet(walletOfCurrentUser);

        stock.setNumberOfStocks(currentStockNumbers - requestNumberOfStocks);
        userRepository.save(currentUser);
        stockRepository.save(stock);
        return totalStockAfterBuy;
    }


}
