package com.example.demo.controller;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.service.LoginService;
import com.example.demo.service.ResponceService;
import com.example.demo.service.TokenService;

import lombok.AllArgsConstructor;

/**
 * AdminRestController.java
 * 管理者アカウントについての操作を実装したクラス
 * @author のうみそ＠overload
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminRestController {

	private final LoginService loginService;
	private final TokenService tokenService;

	/**
	 * 送信されたアカウント情報の照合を行うAPI（管理者用）
	 * @param requestBody ログインフォームに入力されたidとpassword
	 * @return　status:ログインの可否 を含むjson
	 */
	@PostMapping("/login")
	public HashMap<String, Object> login(@RequestBody HashMap<String, Object> requestBody) {
		HashMap<String, Object> responce = new HashMap<>();
		try {
			String cid = (String) requestBody.get("cid");
			String password = (String) requestBody.get("password");

			// 存在しないアカウントならログイン失敗
			Customer loginUser = loginService.findExistAccount(cid, password);
			if (loginUser == null) {
				responce = ResponceService.statusCustom("NotFound");
				return responce;
			}

			// アカウントが管理者用でなければログイン失敗
			Boolean isAdmin = (Objects.nonNull(loginUser.getAdmin())) ? true : false;
			if (!isAdmin) {
				responce = ResponceService.statusCustom("Denied");
				return responce;
			}
			
			
			// ログイン成功時の処理
			String token = tokenService.generateToken(loginUser.getCname(), loginUser.getCid(),
					isAdmin);
			responce = ResponceService.statusSuccess();
			responce.put("token", token);
			return responce;
		} catch (Exception e) {
			responce = ResponceService.statusError();
			System.err.println(e);
			return responce;
		}
	}

}
