package com.stock.rest.webservices.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="wallet_id_seq")
    @SequenceGenerator(name="wallet_id_seq", sequenceName="wallet_id_seq", allocationSize=1,initialValue = 1015)
    private Integer id;

    @OneToOne(mappedBy = "wallet")
    @JsonBackReference
    private User user;

    private Double balance;

    public Wallet(Double balance){
        this.balance=balance;
    }

}
