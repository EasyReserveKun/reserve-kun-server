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

/**
 * AuthController.java
 * ログインtokenを照合するエンドポイントを実装したクラス
 * @author のうみそ＠overload
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
	private final TokenService tokenService;


	/**
	 * 顧客アカウントのtokenを照合するエンドポイント
	 * @param requestBody token情報
	 * @return　status:認証のステータス を含むjson
	 */
	@PostMapping("/customer")
	public HashMap<String, Object> authCustomer(@RequestBody HashMap<String, Object> requestBody) {
		String token = (String) requestBody.get("token");
		HashMap<String, Object> responce = new HashMap<>();

		if (tokenService.validateToken(token)) {
			responce = ResponceService.statusCustom("Accepted");
		} else {
			responce = ResponceService.statusCustom("Denied");
		}
		return responce;
	}
	
	/**
	 * 従業員アカウントのtokenを照合するエンドポイント
	 * @param requestBody token情報
	 * @return　status:認証のステータス を含むjson
	 */
	@PostMapping("/admin")
	public HashMap<String, Object> authAdmin(@RequestBody HashMap<String, Object> requestBody) {
		String token = (String) requestBody.get("token");
		HashMap<String, Object> responce = new HashMap<>();

		// tokenが不正ならその時点で弾く
		if(!tokenService.validateToken(token)) {
			responce = ResponceService.statusCustom("Denied");
			return responce;
		}
		
		// Admin権限があるかを確認する
		if (tokenService.extractIsAdmin(token)) {
			responce = ResponceService.statusCustom("Accepted");
		} else {
			responce = ResponceService.statusCustom("Denied");
		}
		return responce;
	}
}
