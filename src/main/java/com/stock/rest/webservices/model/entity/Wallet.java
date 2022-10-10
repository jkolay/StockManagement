package com.stock.rest.webservices.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Wallet {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(mappedBy = "wallet")
    @JsonBackReference
    private User user;

    private Double balance;

    public Wallet(Double balance){
        this.balance=balance;
    }

}
