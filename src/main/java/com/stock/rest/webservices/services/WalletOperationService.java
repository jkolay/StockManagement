package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.WalletOperationsException;
import com.stock.rest.webservices.model.WalletRequest;
import com.stock.rest.webservices.model.response.WalletResponse;

public interface WalletOperationService {
    public WalletResponse addAmount(WalletRequest wallet) throws WalletOperationsException;

    public WalletResponse checkBalance(long userId) throws WalletOperationsException;
}
