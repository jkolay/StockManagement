package com.stock.rest.webservices.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue
	@Column(name="user_id")
	private long id;
	@NotNull
	@Size(min =5,max=15,message = "Username length should be between 5 and 15")
	private String username;
	@NotNull
	@Size(min =8,max=15,message = "password length should be between 5 and 15")
	private String password;

	@NotNull
	private String role = "ROLE_USER";


	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	@JoinColumn(name = "wallet_id", referencedColumnName = "id")
	private Wallet wallet;

	public User(long id, String username, String password ) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

}