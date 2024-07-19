package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.service.ResponceService;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//  CustomerRestController.java
//  顧客に関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminRestController {

	private final LoginService loginService;

	// 送信されたアカウント情報の照合を行うAPI（管理者用）
	@CrossOrigin
	@PostMapping("/login")
	public HashMap<String, Object> login(@RequestBody CustomerLoginForm customerLoginForm) {

		Customer loginUser = loginService.AdminExist(customerLoginForm);
		HashMap<String, Object> responce = new HashMap<>();
		HashMap<String, Object> results = new HashMap<>();
		if (loginUser != null) {
			responce = ResponceService.responceMaker("Success");
			results.put("mail", loginUser.getCid());
			results.put("name", loginUser.getCname());
			responce.put("results", results);
		} else {
			responce = ResponceService.responceMaker("Error");
		}
		return responce;
	}


}
