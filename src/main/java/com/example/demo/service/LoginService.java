package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

    private final CustomerRepository customerRepository;

	// ユーザ名（cid）とパスワードを受け取り、LoginRepositoryを通じて該当するレコードの数を取得する
	public Customer isAccountExist(CustomerLoginForm customerLoginForm) {
		Optional<Customer> optionalCustomer = customerRepository.findByCidAndPassword(customerLoginForm.getCid(),
				customerLoginForm.getPassword());

		if (optionalCustomer.isEmpty()) {
			return null;
		} else {
			return optionalCustomer.get();
		}
	}
}
