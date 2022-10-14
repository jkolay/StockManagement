package com.stock.rest.webservices.services;

import com.stock.rest.webservices.exception.UserValidationException;
import com.stock.rest.webservices.model.EmailDetails;
import com.stock.rest.webservices.model.UserRequest;
import com.stock.rest.webservices.model.entity.User;
import com.stock.rest.webservices.model.entity.Wallet;
import com.stock.rest.webservices.model.response.UserResponse;
import com.stock.rest.webservices.repository.UserRepository;
import com.stock.rest.webservices.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminOperationsServiceImpl implements AdminOperationsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public UserResponse addUser(UserRequest userRequest) throws UserValidationException {
        if(!userRequest.getRole().equals("ROLE_USER") && !userRequest.equals("ROLE_ADMIN")){
            throw new UserValidationException("Role value should be either ROLE_USER or ROLE_ADMIN" );
        }
        Wallet wallet= Wallet.builder().balance(0D).build();


        User user= User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .email(userRequest.getEmail())
                .wallet(wallet)
                .build();
        wallet.setUser(user);
        userRepository.save(user);
        EmailDetails emailDetails= new EmailDetails();
        emailDetails.setRecipient(userRequest.getEmail());
        emailDetails.setSubject("Welcome to StockReserve");
        emailDetails.setMsgBody("Hi "+userRequest.getUsername()+",\nWelcome to Stock Reserve.You can add money in your wallet and start Trading.\nKind Regards,\nTeam StockReserve");


        User userResponse= userRepository.findByUsername(user.getUsername());
        emailService.sendSimpleMail(emailDetails);

        return UserResponse.builder().id(userResponse.getId()).password(userResponse.getPassword()).role(userResponse.getRole()).username(userResponse.getUsername()).email(userResponse.getEmail()).wallet(userResponse.getWallet()).build();
    }

    @Override
    public List<UserResponse> retrieveUsers() {
        List<User> users= userRepository.findAll();
        List<UserResponse> response = users.stream().map(user -> {
            return UserResponse.builder().id(user.getId()).password(user.getPassword()).role(user.getRole()).username(user.getUsername()).wallet(user.getWallet()).build();

        }).collect(Collectors.toList());
        return response;
    }
}
