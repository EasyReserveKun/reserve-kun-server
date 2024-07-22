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

	/**
	 * 入力されたログイン情報を受け取り一致するアカウントが存在するか確認するメソッド
	 * @param cid 入力されたID(メールアドレス)
	 * @param password 入力されたパスワード
	 * @return アカウントが存在すればアカウント情報を、存在しなかったらnullを返す
	 */
	public Customer findExistAccount(String cid, String password) {
		Optional<Customer> optionalCustomer = customerRepository.findByCidAndPassword(cid, password);
		if(optionalCustomer.isEmpty()) {
			return null;
		}
		return optionalCustomer.get();
	}

	public Customer findExistAccountFromCid(String cid) {
		Optional<Customer> optionalCustomer = customerRepository.findByCid(cid);
		if(optionalCustomer.isEmpty()) {
			return null;
		}
		return optionalCustomer.get();
	}
}
