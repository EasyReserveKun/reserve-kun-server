package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_customer")
public class Customer {

	@Id
	@Column(name = "cid")
	public String cid;

	@Column(name = "cname")
	public String cname;

	@Column(name = "password")
	public String password;

}
