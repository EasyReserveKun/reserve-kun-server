package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.ReserveService;
import com.example.demo.service.ResponceService;
import com.example.demo.service.TokenService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * EmployeeRestController.java
 * 従業員情報についてのエンドポイントを実装したクラス
 * @author のうみそ＠overload
 */
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeRestController {
	private final EmployeeRepository employeeRepository;
	private final ReserveRepository reserveRepository;
	private final ReserveService reserveService;
	private final TokenService tokenService;

	/**
	 * 予約情報をデータベースに登録するエンドポイント
	 * @param reserveForm 予約の詳細情報を持つデータ
	 * @return　予約処理のステータス
	 */
	@GetMapping("list")
	public HashMap<String, Object> list() {
		HashMap<String, Object> responce = new HashMap<>();
		List<Employee> empList = employeeRepository.findAll();
		responce.put("results", empList);
		responce.put("type", "Employee");
		responce.put("status", "success");
		return responce;

	}

	/**
	 * 管理者側で行う予約停止のためのエンドポイント
	 * @param reserveForm 予約の詳細情報を持つデータ
	 * @return　予約処理のステータス
	 */
	@Transactional
	@PostMapping("stop")
	public HashMap<String, Object> stop(@RequestBody ReserveForm reserveForm) {
		HashMap<String, Object> response = new HashMap<>();
		String cid = tokenService.extractUserId(reserveForm.getToken());
		Reserve reserve = reserveForm.getEntity(cid, "1");

		if ("すべての時間".equals(reserve.getTime())) {
			response = processAllTimes(reserve);
		} else {
			response = processSpecificTime(reserve);
		}

		return response;
	}

	private HashMap<String, Object> processAllTimes(Reserve reserve) {
		HashMap<String, Object> response = new HashMap<>();
		String[] timeArray = {
				"10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00"
				};
		
		for (String time : timeArray) {
			if (reserveRepository.findAllTimesByDateAndEidAndTime(reserve.getDate(), reserve.getEid(), reserve.getTime()) == null) {
				reserve.setTime(time);

				Reserve reserveDuplicated = reserveService.findReserveDuplicated(reserve);

				if (Objects.isNull(reserveDuplicated)) {
					reserveRepository.save(reserve);
					response = ResponceService.statusSuccess();
				} else {
					System.out.println("Duplicated");
					response = ResponceService.statusCustom("Duplicated");
				}
			}
		}
		return response;
	}

	private HashMap<String, Object> processSpecificTime(Reserve reserve) {
		Reserve reserveDuplicated = reserveService.findReserveDuplicated(reserve);

		if (Objects.isNull(reserveDuplicated)) {
			reserveRepository.saveAndFlush(reserve);
			return ResponceService.statusSuccess();
		} else {
			return ResponceService.statusCustom("Duplicated");
		}
	}

	/**
	 * 管理者側で行う即時予約停止のためのエンドポイント
	 * @param requestBody 絞り込みを行うための従業員番号
	 * @return　予約処理のステータス
	 */
	@Transactional
	@PostMapping("stopAll")
	public String stopAll(@RequestBody HashMap<String, Object> requestBody) {

		String eid = (String) requestBody.get("eid");
		if ("".equals(eid)) {
			return "従業員を選択してください";
		} else {
			int count = employeeRepository.updateSetFlag(eid);

			if (count == 1) {
				return "受付を停止します";
			} else {
				return "エラーが発生しました";
			}
		}
	}

	/**
	 * 管理者側で行う即時受付停止の解除のためのエンドポイント
	 * @param requestBody 絞り込みを行うための従業員番号
	 * @return　予約処理のステータス
	 */
	@Transactional
	@PostMapping("reactivate")
	public String reactivate(@RequestBody HashMap<String, Object> requestBody) {

		String eid = (String) requestBody.get("eid");
		if ("".equals(eid)) {
			return "従業員を選択してください";
		} else {
			int count = employeeRepository.updateDeleteFlag(eid);

			if (count == 1) {
				return "受付を開始します";
			} else {
				return "エラーが発生しました";
			}
		}
	}
	
	/**
	 * 管理者側で即時停止中の従業員を区別するためのエンドポイント
	 * @param requestBody 何も送ってない
	 * @return　従業員のステータス（１：停止中、０：受付中）
	 */
	@PostMapping("flagCheck")
	public List<String> flagCheck(@RequestBody HashMap<String, Object> requestBody) {
	    List<String> list = new ArrayList<>(); // リストを初期化
	    String[] id = {"1", "2", "3", "4", "5"};

	    for (String s : id) {
	    	List<Employee> employees = employeeRepository.findAllByEidAndStop_flag(s, "1");
	        if (!employees.isEmpty()) {
	            list.add("0");
	        } else {
	            list.add("1");
	        }
	    }
	    return list;
	}
}
