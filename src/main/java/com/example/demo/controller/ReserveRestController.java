package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveCheckForm;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.EncodeService;
import com.example.demo.service.ReserveService;
import com.example.demo.service.ResponceService;
import com.example.demo.service.TokenService;

import lombok.AllArgsConstructor;


/**
 * ReserveRestController.java
 * 予約に関する情報を取り扱うエンドポイントを実装するクラス
 * @author のうみそ＠overload
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/reserve")
public class ReserveRestController {

	private final ReserveRepository reserveRepository;
	private final ReserveService reserveService;
	private final TokenService tokenService;
	private final EmployeeRepository employeeRepository;


	/**
	 * 予約情報をデータベースに登録するエンドポイント
	 * @param reserveForm 予約の詳細情報を持つデータ
	 * @return　status:予約処理のステータス を含むjson
	 */
	@PostMapping("/insert")
	public HashMap<String, Object> insert(@RequestBody ReserveForm reserveForm) {
		HashMap<String, Object> responce = new HashMap<>();
		String cid = null;
		try {
			cid = tokenService.extractUserId(reserveForm.getToken());

		} catch (Exception e) {
			responce = ResponceService.statusCustom("Denied");
			return responce;
		}

		Reserve reserve = reserveForm.getEntity(cid, null);

		// 予約の重複チェック
		String error = reserveService.checkReserveDuplicatedOrDoubled(reserve);
		
		// 文字コードチェック
		if (!EncodeService.canEncodeToSJIS(new String[]{ reserveForm.getEtc() })) {
			responce = ResponceService.statusError();
			return responce;
		}

		
		if (error.isEmpty()) {
			// データベースへの書き込み
			reserveRepository.saveAndFlush(reserve);
			responce = ResponceService.statusSuccess();
		} else {
			responce = ResponceService.statusError();
		}

		return responce;
	}

	/**
	 * 予約可能な時間帯を確認するためのエンドポイント
	 * @param reserveForm 予約する日付と従業員
	 * @return　その日、その従業が予約を受け入れられる時間のリスト
	 */
	@PostMapping("/available")
	public List<String> available(@RequestBody ReserveCheckForm reserveCheckForm) {
		// @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

		// 日付と社員IDに基づいてリポジトリから時間のリストを取得します
		List<String> list = reserveRepository.findAllTimesByDateAndEid(
				reserveCheckForm.getDate(), // フォームオブジェクトから日付を取得します
				reserveCheckForm.getEid() // フォームオブジェクトから社員IDを取得します
		);

		return list; // 時間のリストをレスポンスとして返します
	}

	/**
	 * ユーザーの予約履歴を確認するためのエンドポイント
	 * @param requestBody ユーザーのtoken
	 * @return　ユーザーの全ての予約情報
	 */
	@PostMapping("/check")
	public List<Reserve> check(@RequestBody HashMap<String, String> requestBody) {
		String token = requestBody.get("token");
		List<Reserve> list = reserveRepository.findAllByCidOrderByDate(tokenService.extractUserId(token));
    
		return list;
	}

	/**
	 * 管理者画面で予約一覧を確認するためのエンドポイント
	 * @param requestBody 絞り込みを行うための従業員番号
	 * @return 全て、またはeidで絞り込んだ予約情報
	 */
	@PostMapping("/employeecheck")
	public List<Map<String, Object>> employeeCheck(@RequestBody HashMap<String, String> requestBody) {
		List<Map<String, Object>> list;
		String eid = requestBody.get("eid");
		if ("all".equals(eid)) {
			list = reserveRepository.findAllByOrderByDate();
		} else {
			list = reserveRepository.findAllByEidOrderByDate(eid);
		}
		return list;
	}


	/**
	 * 管理者画面で予約をキャンセルするためのエンドポイント
	 * @param reserveForm 予約された日付と時間と従業員
	 * @return キャンセルの可否
	 */
	@PostMapping("/cancel")
	public String cancel(@RequestBody ReserveForm reserveForm) {

		List<Reserve> reservations = reserveRepository.findByDateAndTimeAndEid(reserveForm.getDate(),
				reserveForm.getTime(), reserveForm.getEid());
		if (!reservations.isEmpty()) {
			reserveRepository.deleteAll(reservations);
			return "予約をキャンセルしました";
		} else {
			return "該当する予約が見つかりませんでした";
		}
	}

	/**
	 * 管理者画面で予約を停止している日時を確認するためのエンドポイント
	 * @param  ReserveCheckForm　使用してません
	 * @return 予約を停止している日時と従業員IDを取得
	 */
	@PostMapping("/unavailable")
	public List<String> unavailable(@RequestBody ReserveCheckForm reserveCheckForm) {
		// @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

		// 日付と社員IDに基づいてリポジトリから時間のリストを取得します
		List<String> list = reserveRepository.findAllByFlag("1");

		return list; // 時間のリストをレスポンスとして返します
	}

	
	@GetMapping("reservelist")
	public HashMap<String, Object> customerlist() {
		HashMap<String, Object> responce = new HashMap<>();
		List<Reserve> reserveList = reserveRepository.findAll();
		responce = ResponceService.statusSuccess();
		responce.put("results", reserveList);
		responce.put("type", "Customer");
		return responce;
	}
    
	/**
	 * ユーザー側でサーチする際に、受付停止中の従業員IDを区別するためのエンドポイント
	 * @param requestBody 絞り込みを行うための従業員番号
	 * @return 予約の可否
	 */
	@PostMapping("/available/flag")
	public String available(@RequestBody HashMap<String, Object> requestBody) {
		// @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

	  String eid = (String) requestBody.get("eid");
		// 日付と社員IDに基づいてリポジトリから時間のリストを取得します
		int count = employeeRepository.findAllByEid(eid);

		if (count == 1) {
			return "現在は予約を受け付けておりません"; // 時間のリストをレスポンスとして返します
		} else {
			return "予約可能です";
			
		}
	}
}
