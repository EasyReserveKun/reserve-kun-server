package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.form.CustomerForm;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.MailSenderServise;
import com.example.demo.service.ResponceService;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//  CustomerRestController.java
//  顧客に関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerRestController {

	private final CustomerRepository customerRepository;
	private final LoginService loginService;
	private final MailSenderServise msService;

	// 送信されたアカウント情報の照合を行うAPI
	@CrossOrigin
	@PostMapping("/login")
	public HashMap<String, Object> login(@RequestBody CustomerLoginForm customerLoginForm) {
		Customer loginUser = loginService.isAccountExist(customerLoginForm);
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

	// 登録情報を受け取り、認証コードを送信
	@CrossOrigin
	@PostMapping("/signup")
	public HashMap<String, Object> signup(@RequestBody CustomerForm customerForm) {
		// 共通の処理
		String code = generateCode();
		HashMap<String, Object> responce = new HashMap<>();
		
		// アカウントが新規かどうかの判定
		Customer signupUser = loginService.isAccountExist(customerForm);
		if (signupUser == null) {
			//メールの送信
			String title = "【かんたん予約くん】メールアドレス認証のお願い";
			String message = "○○様\n"+
							"ACE社コンシェルジュデスク予約サービス「かんたん予約くん」にご登録いただきありがとうございます。\n" +
							"アカウントは現在、仮登録状態です。下記コードを入力して、本登録を行ってください。 \n\n" +
							"認証コード：" + code + "\n\n" +
							"今後ともACE社およびかんたん予約くんをよろしくお願いいたします。 \n"+
							"ACE";
			msService.sendSimpleMessage(customerForm.getCid(), title, message);
			responce = ResponceService.responceMaker("Success");
			responce.put("code", code);
		} else {
			responce = ResponceService.responceMaker("Error");
		}
		return responce;
	}
	// 認証コードが通った先の処理
	@CrossOrigin
	@PostMapping("/verify")
	public HashMap<String, Object> verify(@RequestBody CustomerForm customerForm) {
		HashMap<String, Object> responce = new HashMap<>();
		Customer signupUser = loginService.isAccountExist(customerForm);
		if (signupUser == null) {
			customerRepository.insertUser(customerForm.getCid(), customerForm.getCname(), customerForm.getPassword());
			responce = ResponceService.responceMaker("Success");
		} else {
			responce = ResponceService.responceMaker("Error");
		}
		return responce;
	}
	
	public static String generateCode() {
		String code;
		while (true) {
			code = String.format("%06d", (int) (Math.random() * 1000000));
			if (!isQuadrapleSameDigits(code)) {
				break;
			}
		}
		return code;
	}

	public static Boolean isQuadrapleSameDigits(String number) {
		for (int i = 1; i < number.length() - 3; i++) {
			char digit1 = number.charAt(i);
			char digit2 = number.charAt(i + 1);
			char digit3 = number.charAt(i + 2);
			char digit4 = number.charAt(i + 3);
			if (digit1 == digit2 && digit2 == digit3 && digit3 == digit4) {
				return true;
			}
		}
		return false;
	}

}
