/**
 * EmployeeRestController.java
 * 従業員情報に関する情報を取り扱うクラス
 * @author のうみそ＠overload
 */
package com.example.demo.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Reserve;
import com.example.demo.form.EmployeeForm;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.ReserveService;
import com.example.demo.service.ResponceService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeRestController {
	private final EmployeeRepository employeeRepository;
	private final ReserveRepository reserveRepository;
	private final ReserveService reserveService;

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

	@Transactional
	@PostMapping("stop")
	public HashMap<String, Object> stop(@RequestBody ReserveForm reserveForm) {

		String[] timeArray = {
				"10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
				"16:00", "17:00", "18:00", "19:00"
		};

		HashMap<String, Object> response = new HashMap<>();
		Date date = reserveForm.getDate();
		String time = reserveForm.getTime();
		String eid = reserveForm.getEid();

		if ("すべての時間".equals(time)) {
			response = processAllTimes(date, eid, timeArray, reserveForm);
		} else {
			response = processSpecificTime(date, eid, reserveForm);
		}

		return response;
	}

	private HashMap<String, Object> processAllTimes(Date date, String eid, String[] timeArray,
			ReserveForm reserveForm) {
		boolean isReserved = false;
		HashMap<String, Object> response = new HashMap<>();
		for (String time : timeArray) {
			if (reserveRepository.findAllTimesByDateAndEidAndTime(date, eid, time) == null) {
				reserveForm.setTime(time);
				System.out.println("Setting time to: " + time);

				Reserve reserve = reserveForm.insertEntity();
				String error = reserveService.reserveEmployeeCheck(reserveForm);

				if (error.isEmpty()) {
					reserveRepository.save(reserve);
					isReserved = true;
					response = ResponceService.responceMaker("Success");
				} else {
					System.out.println(error);
					response = ResponceService.responceMaker(error);
					isReserved = true;
				}
			}
		}
		if (!isReserved) {
			System.out.println("No available time slots");
			response = ResponceService.responceMaker("No available time slots");
		}
		return response;
	}

	private HashMap<String, Object> processSpecificTime(Date date, String eid, ReserveForm reserveForm) {
		reserveForm.setTime(reserveForm.getTime()); // 明示的に時間を設定
		Reserve reserve = reserveForm.insertEntity();
		String error = reserveService.reserveExceptionChecker(reserveForm);

		if (error.isEmpty()) {
			reserveRepository.saveAndFlush(reserve);
			return ResponceService.responceMaker("Success");
		} else {
			return ResponceService.responceMaker(error);
		}
	}

	@Transactional
	@PostMapping("reactivation")
	public String reactivation(@RequestBody ReserveForm reserveForm) {
		Date date = reserveForm.getDate();
		String time = reserveForm.getTime();
		String eid = reserveForm.getEid();

		if ("すべての時間".equals(time)) {
			int count = 0;
			for (String timeSlot : new String[] { "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
					"17:00", "18:00", "19:00" }) {
				count += reserveRepository.deleteByDateAndTimeAndEidAndStopFlag(date, eid, timeSlot);
			}
			if (count > 0) {
				return "すべての時間の受付を開始します";
			} else {
				return "エラーが発生しました";
			}
		} else {
			int count = reserveRepository.deleteByDateAndTimeAndEidAndStopFlag(date, eid, time);
			if (count == 1) {
				return "受付を開始します";
			} else {
				return "エラーが発生しました";
			}
		}
	}

	@Transactional
	@PostMapping("stopAll")
	public String stopAll(@RequestBody EmployeeForm employeeForm) {

		System.out.println(employeeForm.getEid());
		if ("".equals(employeeForm.getEid())) {
			return "従業員を選択してください";
		} else {
			int count = employeeRepository.updateSetFlag(employeeForm.getEid());

			if (count == 1) {
				return "受付を停止します";
			} else {
				return "エラーが発生しました";
			}
		}
	}

	@Transactional
	@PostMapping("reactivate")
	public String reactivate(@RequestBody EmployeeForm employeeForm) {

		if ("".equals(employeeForm.getEid())) {
			return "従業員を選択してください";
		} else {
			int count = employeeRepository.updateDeleteFlag(employeeForm.getEid());

			if (count == 1) {
				return "受付を開始します";
			} else {
				return "エラーが発生しました";
			}
		}
	}
}
