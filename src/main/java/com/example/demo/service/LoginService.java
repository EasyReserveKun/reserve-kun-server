package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.form.CustomerForm;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

	private final CustomerRepository customerRepository;
	private final AdminRepository adminRepository;

	// ユーザ名（cid）とパスワードを受け取り、LoginRepositoryを通じて該当するレコードの数を取得する
	public Customer isAccountExist(CustomerLoginForm customerLoginForm) {
		Optional<Customer> optionalCustomer = customerRepository.findByCidAndPassword(customerLoginForm.getCid(),
				customerLoginForm.getPassword());

		if (optionalCustomer.isEmpty()) {
	        return null;
	    } else {
	        Customer customer = optionalCustomer.get();
	        if (!("1".equals(customer.getAdmin()))) { // adminが1の場合のみ返す(1の場合は管理者アカウント)
	            return customer;
	        } else {
	            return null; // adminが1でない場合はnullを返す
	        }
	    }
	}

	public Customer isAccountExist(CustomerForm customerForm) {
		Optional<Customer> optionalCustomer = customerRepository.findByCid(customerForm.getCid());

		if (optionalCustomer.isEmpty()) {
			return null;
		} else {
			return optionalCustomer.get();
		}
	}
	
	public Customer AdminExist(CustomerLoginForm customerLoginForm) {
	    Optional<Customer> optionalCustomer = adminRepository.findByCidAndPassword(
	        customerLoginForm.getCid(), customerLoginForm.getPassword());

	    if (optionalCustomer.isEmpty()) {
	        return null;
	    } else {
	        Customer customer = optionalCustomer.get();
	        if ("1".equals(customer.getAdmin())) { // adminが1の場合のみ返す(1の場合は管理者アカウント)
	            return customer;
	        } else {
	            return null; // adminが1でない場合はnullを返す
	        }
	    }
	}
}
