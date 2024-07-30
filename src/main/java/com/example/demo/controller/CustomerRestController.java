package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.ExpireMinutesConfig;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Temporary;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.TemporaryRepository;
import com.example.demo.service.EncodeService;
import com.example.demo.service.LoginService;
import com.example.demo.service.MailSenderServise;
import com.example.demo.service.ResponceService;
import com.example.demo.service.SignupService;
import com.example.demo.service.TokenService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * CustomerRestController.java
 * 顧客情報についてのエンドポイントを実装したクラス
 * @author のうみそ＠overload
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerRestController {

	private final CustomerRepository customerRepository;
	private final TemporaryRepository temporaryRepository;
	private final TokenService tokenService;
	private final LoginService loginService;
	private final MailSenderServise msService;
	private final SignupService signupService;
	private final ExpireMinutesConfig emConfig;


	/**
	 * ログイン情報を照合するエンドポイント
	 * @param requestBody 入力されたidとpassword
	 * @return　status:ステータス, token:トークン を含むjson
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
				responce = ResponceService.responceMaker("NotExist");
				return responce;
			}

			// アカウントが管理者用ならログイン失敗
			Boolean isAdmin = (Objects.nonNull(loginUser.getAdmin())) ? true : false;
			if (isAdmin) {
				responce = ResponceService.responceMaker("Denied");
				return responce;
			}

			// ログイン成功時の処理
			String token = tokenService.generateToken(loginUser.getCname(), loginUser.getCid(), isAdmin);
			responce = ResponceService.responceMaker("Success");
			responce.put("token", token);
			return responce;
		} catch (Exception e) {
			responce = ResponceService.responceMaker("Error");
			System.err.println(e);
			return responce;
		}
	}

	/**
	 * アカウント登録を行うエンドポイント
	 * @param customerForm フォームに入力された名前、id、password
	 * @return　status:ステータス を含むjson
	 */
	@Transactional
	@PostMapping("/signup")
	public HashMap<String, Object> signup(@RequestBody SignupForm customerForm) {
		// 共通の処理
		HashMap<String, Object> responce = new HashMap<>();
		String code = generateCode();

		// 既に同じIDで登録されている場合はエラーを返す
		Customer signupUser = loginService.findExistAccount(customerForm.getCid(), customerForm.getPassword());
		if (signupUser != null) {
			responce = ResponceService.responceMaker("Duplicate");
			return responce;
		}

		if (!EncodeService.canEncodeToSJIS(
				new String[] { customerForm.getCid(), customerForm.getCname(), customerForm.getPassword() })) {
			responce = ResponceService.responceMaker("Error");
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
					+ "下記の認証コードを入力して手続きを完了してください。\n\n" + "認証コード：" + code + "\n\n"
					+ emConfig.getCodeExpiryMinutesString() + "分以内に手続きが完了しない場合、仮登録が無効となります。\n"
					+ "その際は再度はじめから登録をやり直してください。\n\n" + "※このメールは、送信専用メールアドレスから配信されています。\n"
					+ "※ご返信いただいてもお答えできませんので、ご了承ください。";

			msService.sendSimpleMessage(customerForm.getCid(), title, message);
			responce = ResponceService.responceMaker("Success");
			return responce;
		} catch (Exception e) {
			responce = ResponceService.responceMaker("Error");
			return responce;
		}
	}

	/**
	 * 認証コードの照合を行うエンドポイント
	 * @param requestBody フォームに入力された認証コード
	 * @return　status:ステータス を含むjson
	 */
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
		LocalDateTime dateExpire = verifyCustomer.getDate().plusMinutes(emConfig.getCodeExpiryMinutes());
		if (dateNow.isAfter(dateExpire)) {
			temporaryRepository.delete(verifyCustomer);
			responce = ResponceService.responceMaker("NotFound");
			return responce;
		}

		// 既に同じIDで登録されている場合はエラーを返す
		Customer existingUser = loginService.findExistAccountFromCid(verifyCustomer.getCid());
		if (existingUser != null) {
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
		} catch (Exception e) {
			responce = ResponceService.responceMaker("Error");
			return responce;
		}

		return responce;
	}

	/**
	 * ユーザーtokenから登録名を抽出するエンドポイント
	 * @param requestBody ログインされているユーザーのtoken
	 * @return　status:ステータス, name:抽出したユーザ名 を含むjson
	 */
	@PostMapping("/getname")
	public HashMap<String, Object> getName(@RequestBody HashMap<String, String> requestBody) {
		HashMap<String, Object> responce = new HashMap<>();
		try {
			String token = requestBody.get("token");
			responce = ResponceService.responceMaker("Success");
			responce.put("name", tokenService.extractUsername(token));
			return responce;
		} catch (Exception e) {
			responce = ResponceService.responceMaker("Error");
			return responce;
		}
	}

	/**
	 * デバッグ用 仮登録テーブルを確認するためのエンドポイント
	 * @return　type:Temporary, results:全仮登録データ を含むjson
	 */
	@GetMapping("templist")
	public HashMap<String, Object> templist() {
		HashMap<String, Object> responce = new HashMap<>();
		List<Temporary> tempList = temporaryRepository.findAll();
		responce = ResponceService.responceMaker("Success");
		responce.put("results", tempList);
		responce.put("type", "Temporary");
		return responce;
	}

	/**
	 * デバッグ用 顧客テーブルを確認するためのエンドポイント
	 * @return　type:Temporary, results:全仮登録データ を含むjson
	 */
	@GetMapping("customerlist")
	public HashMap<String, Object> customerlist(@RequestParam(name = "cid", required = false) String cid) {
		HashMap<String, Object> responce = new HashMap<>();
		List<Customer> customerList = customerRepository.findAll();

		try {
			if (cid != null) {
				customerRepository.deleteByCid(cid);
			}
		} catch (Exception e) {
			System.err.println(e);
		}

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

	@CrossOrigin
	@Transactional
	@PostMapping("leave")
	public HashMap<String, Object> leave(@RequestBody HashMap<String, String> requestBody) {
		HashMap<String, Object> responce = new HashMap<>();
		// ログイン成功時の処理
		String cid = tokenService.extractUserId(requestBody.get("token"));
		//int count = customerRepository.deleteAllByCid(cid);
		int count=1;
		if (count == 1) {
			responce = ResponceService.responceMaker("Success");
		} else {
			responce = ResponceService.responceMaker("Error");
		}
		return responce;
	}
}
