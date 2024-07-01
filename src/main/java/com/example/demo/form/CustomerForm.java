package com.example.demo.form;

import com.example.demo.entity.Customer;

import lombok.Data;
@Data
public class CustomerForm {


	private String cid;

	private String cname;

	private String password;

	public Customer getEntity() {

		Customer customer = new Customer();
		customer.setCid(cid);
		customer.setCname(cname);
		customer.setPassword(password);
		return customer;
	}
}
