package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.WalletOperationsException;
import com.stock.rest.webservices.model.WalletRequest;
import com.stock.rest.webservices.model.entity.User;
import com.stock.rest.webservices.model.entity.Wallet;
import com.stock.rest.webservices.model.response.WalletResponse;
import com.stock.rest.webservices.repository.UserRepository;
import com.stock.rest.webservices.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletOperationServiceImpl implements WalletOperationService{
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public WalletResponse addAmount(WalletRequest wallet) throws WalletOperationsException {
        User user =userRepository.findById(wallet.getUserId()).orElseThrow(() ->
            new WalletOperationsException(String.format("User id is not correct.Please check userID")));
        Wallet existingWallet= user.getWallet();
        Double amount= existingWallet.getBalance()+wallet.getAmount();
        existingWallet.setBalance(amount);
        Wallet responseWallet=walletRepository.save(existingWallet);
        return WalletResponse.builder().balance(responseWallet.getBalance()).build();
    }

    @Override
    public WalletResponse checkBalance(long userId) throws WalletOperationsException {
        User user =userRepository.findById(userId).orElseThrow(() ->
                new WalletOperationsException(String.format("User id is not correct.Please check userID")));
        Wallet existingWallet= user.getWallet();
        Double amount= existingWallet.getBalance();
       return WalletResponse.builder().balance(amount).build();
    }
}
