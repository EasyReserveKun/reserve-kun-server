package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

/**
 * LoginService.java
 * 存在するアカウントを確認するための機能を実装するクラス
 * @author のうみそ＠overload
 */
@Service
@AllArgsConstructor
public class LoginService {

	private final CustomerRepository customerRepository;

	/**
	 * メールアドレスとパスワードが一致するアカウントが存在するか確認するメソッド
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

	/**
	 * メールアドレスが一致するアカウントが存在するか確かめるメソッド
	 * @param cid ID(メールアドレス)
	 * @return アカウントが存在すればアカウント情報を、存在しなかったらnullを返す
	 */
	public Customer findExistAccountFromCid(String cid) {
		Optional<Customer> optionalCustomer = customerRepository.findByCid(cid);
		if(optionalCustomer.isEmpty()) {
			return null;
		}
		return optionalCustomer.get();
	}
}
