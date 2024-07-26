package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ResponceService;
import com.example.demo.service.TokenService;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//CustomerRestController.java
//顧客に関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final TokenService tokenService;

	@CrossOrigin
	@PostMapping("/customer")
	public HashMap<String, Object> authCustomer(@RequestBody HashMap<String, Object> requestBody) {
		String token = (String) requestBody.get("token");
		HashMap<String, Object> responce = new HashMap<>();

		if (tokenService.validateToken(token)) {
			responce = ResponceService.responceMaker("Accepted");
		} else {
			responce = ResponceService.responceMaker("Denied");
		}
		return responce;
	}
	
	@CrossOrigin
	@PostMapping("/admin")
	public HashMap<String, Object> authAdmin(@RequestBody HashMap<String, Object> requestBody) {
		String token = (String) requestBody.get("token");
		HashMap<String, Object> responce = new HashMap<>();

		if (tokenService.validateToken(token) && tokenService.extractIsAdmin(token)) {
			responce = ResponceService.responceMaker("Accepted");
		} else {
			responce = ResponceService.responceMaker("Denied");
		}
		return responce;
	}
}
