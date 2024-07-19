package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Reserve;
import com.example.demo.form.BookingCheckerForm;
import com.example.demo.form.ReserveCheckForm;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.ReserveService;
import com.example.demo.service.ResponceService;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//  ReserveRestController.java
//  予約に関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/reserve")
public class ReserveRestController {

	 // ReserveRepositoryのインジェクション
    private final ReserveRepository reserveRepository;
    private final ReserveService reserveService;

    // 予約情報の追加--------------------------------------------------
    @CrossOrigin
    @PostMapping("/insert")
    public HashMap<String, Object> insert(@RequestBody ReserveForm reserveForm) {
    	HashMap<String, Object> responce = new HashMap<>();
        Reserve reserve = reserveForm.getEntity();

        // エラーチェック
        String error = reserveService.reserveExceptionCheck(reserveForm);
        if(error.isEmpty()) {
            // データベースへの書き込み
            reserveRepository.saveAndFlush(reserve);
            responce = ResponceService.responceMaker("Success");
        } else {
        	responce = ResponceService.responceMaker(error);
        }

		return responce;
    }

    // 予約可能な時間の確認--------------------------------------------------
    @CrossOrigin
    @PostMapping("/available")
    public List<String> available(@RequestBody ReserveCheckForm reserveCheckForm) {
        // @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

        // 日付と社員IDに基づいてリポジトリから時間のリストを取得します
        List<String> list = reserveRepository.findAllTimesByDateAndEid(
            reserveCheckForm.getDate(),  // フォームオブジェクトから日付を取得します
            reserveCheckForm.getEid()    // フォームオブジェクトから社員IDを取得します
        );
        
        return list;  // 時間のリストをレスポンスとして返します
    }

    //--------------------------------------------------
    @CrossOrigin
	@PostMapping("/check")
	public List <Reserve> check(@RequestBody BookingCheckerForm bookingCheckerForm) {
		List<Reserve> list = reserveRepository.findAllByCidOrderByDate(bookingCheckerForm.getCid());
		
		return list;
	}
    
    @CrossOrigin
   	@PostMapping("/employeeCheck")
   	public List <Reserve> employeeCheck(@RequestBody BookingCheckerForm bookingCheckerForm) {
    	List<Reserve> list;
    	if("all".equals(bookingCheckerForm.getEid())){
    		 list = reserveRepository.findAllByOrderByDate();
    	}else {
    		 list = reserveRepository.findAllByEidOrderByDate(bookingCheckerForm.getEid());
    	}
   		return list;
   	}
    
    @CrossOrigin
	@PostMapping("/cancel")
	public String cancel(@RequestBody ReserveForm reserveForm) {
    	
    	List<Reserve> reservations = 
    			reserveRepository.findByDateAndTimeAndEid(reserveForm.getDate(), reserveForm.getTime(), reserveForm.getEid());
        if (!reservations.isEmpty()) {
            reserveRepository.deleteAll(reservations);
            return "予約をキャンセルしました";
        } else {
            return "該当する予約が見つかりませんでした";
        }
	}
    
    @CrossOrigin
    @PostMapping("/unavailable")
    public List<String> unavailable(@RequestBody ReserveCheckForm reserveCheckForm) {
        // @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

        // 日付と社員IDに基づいてリポジトリから時間のリストを取得します
        List<String> list = reserveRepository.findAllTimesByDateAndEidAndFlag(
            reserveCheckForm.getDate(),  // フォームオブジェクトから日付を取得します
            reserveCheckForm.getEid(),// フォームオブジェクトから社員IDを取得します
            "1"//予約停止フラグ
        );

        return list;  // 時間のリストをレスポンスとして返します
    }
}
