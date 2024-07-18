package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Temporary;
import com.example.demo.form.CustomerForm;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TemporaryRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.MailSenderServise;
import com.example.demo.service.ResponceService;
import com.example.demo.service.SignupService;

import jakarta.transaction.Transactional;
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
	private final TemporaryRepository temporaryRepository;
	private final LoginService loginService;
	private final MailSenderServise msService;
	private final SignupService signupService;

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
	@Transactional
	@PostMapping("/signup")
	public HashMap<String, Object> signup(@RequestBody CustomerForm customerForm) {
		// 共通の処理
		HashMap<String, Object> responce = new HashMap<>();
		String code = generateCode();

		// 既に同じIDで登録されている場合はエラーを返す
		Customer signupUser = loginService.isAccountExist(customerForm);
		if (signupUser != null) {
			responce = ResponceService.responceMaker("Duplicate");
			return responce;
		}

		try {
			// 仮登録情報をDBに登録
			temporaryRepository.generateTemp(customerForm.getCid(), customerForm.getCname(), customerForm.getPassword(),
					LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), code);
			// 仮登録完了メールの送信
			String title = "【かんたん予約くん】仮登録のご案内";
			String message = customerForm.getCname() + "様\n\n" + "ACE社コンシェルジュデスク予約サービス「かんたん予約くん」にご登録いただきありがとうございます。\n\n"
					+ "会員情報の「仮登録」を受け付けました。\n" + "※登録はまだ完了していません。\n\n" + "当サービスをご利用いただくには「本登録」の手続きが必要です。\n"
					+ "下記の認証コードを入力して手続きを完了してください。\n\n" + "認証コード：" + code + "\n\n" + "30分以内に手続きが完了しない場合、仮登録が無効となります。\n"
					+ "その際は再度はじめから登録をやり直してください。\n\n" + "※このメールは、送信専用メールアドレスから配信されています。\n"
					+ "※ご返信いただいてもお答えできませんので、ご了承ください。";
			msService.sendSimpleMessage(customerForm.getCid(), title, message);
			responce = ResponceService.responceMaker("Success");
			responce.put("code", code);
			return responce;
		} catch (Exception e) {
			responce = ResponceService.responceMaker("Error");
			return responce;
		}
	}

	// 認証コードが通った先の処理
	@CrossOrigin
	@Transactional
	@PostMapping("/verify")
	public HashMap<String, Object> verify(@RequestBody HashMap<String, Object> requestBody) {
		HashMap<String, Object> responce = new HashMap<>();
		String uuid = (String) requestBody.get("code");
		// uuidからアカウント情報の取得
		Temporary verifyCustomer = signupService.getCustomerFormFromUuid(uuid);

		// uuidが存在しなかったらエラーを返す
		if (verifyCustomer == null) {
			responce = ResponceService.responceMaker("NotFound");
			return responce;
		}
		
		// 認証コードが期限切れならエラーを返す
		LocalDateTime dateNow = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		LocalDateTime dateExpire = verifyCustomer.getDate().plusMinutes(1);
		if(dateNow.isAfter(dateExpire)) {
			temporaryRepository.delete(verifyCustomer);
			responce = ResponceService.responceMaker("NotFound");
			return responce;
		}
		
		// 既に同じIDで登録されている場合はエラーを返す
		CustomerForm customerForm = new CustomerForm();
		customerForm.setCid(verifyCustomer.getCid());
		Customer existingUser = loginService.isAccountExist(customerForm);
		if (existingUser == null) {
			responce = ResponceService.responceMaker("Duplicate");
			return responce;
		}
		
		// m_customerテーブルに入力
		try {
		Customer customer = new Customer();
		customer.setCid(verifyCustomer.getCid());
		customer.setCname(verifyCustomer.getCname());
		customer.setPassword(verifyCustomer.getPassword());
		customerRepository.save(customer);
		// m_temporaryテーブルから削除
		temporaryRepository.delete(verifyCustomer);
		// レスポンスを返す
		responce = ResponceService.responceMaker("Success");
		} catch(Exception e) {
			responce = ResponceService.responceMaker("Error");
			return responce;
		}

		return responce;
	}

	@CrossOrigin
	@GetMapping("templist")
	public HashMap<String, Object> templist() {
		HashMap<String, Object> responce = new HashMap<>();
		List<Temporary> tempList = temporaryRepository.findAll();
		responce = ResponceService.responceMaker("Success");
		responce.put("results", tempList);
		responce.put("type", "Temporary");
		return responce;
	}

	@CrossOrigin
	@GetMapping("customerlist")
	public HashMap<String, Object> customerlist() {
		HashMap<String, Object> responce = new HashMap<>();
		List<Customer> customerList = customerRepository.findAll();
		responce = ResponceService.responceMaker("Success");
		responce.put("results", customerList);
		responce.put("type", "Customer");
		return responce;
	}

	public String generateCode() {
		String code;
		while (true) {
			code = String.format("%06d", (int) (Math.random() * 1000000));
			if (!isQuadrapleSameDigits(code) && signupService.isNotUuidDuplicate(code)) {
				break;
			}
		}
		return code;
	}

	public Boolean isQuadrapleSameDigits(String number) {
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
