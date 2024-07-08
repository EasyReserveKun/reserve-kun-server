package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.entity.Reserve;
import com.example.demo.form.BookingCheckerForm;
import com.example.demo.form.ReserveCheckForm;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.UrlService;

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

    // 予約情報の追加--------------------------------------------------
    @CrossOrigin
    @PostMapping("/insert")
    public RedirectView insert(@ModelAttribute ReserveForm reserveForm) {
    	// フォーム情報の受け取り
        Reserve reserve = reserveForm.getEntity();

        // データベースへの書き込み
        reserveRepository.saveAndFlush(reserve);

        // 書き込み後、ホームへリダイレクト
        return new RedirectView(UrlService.getWebUrl());
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
	public List <Reserve> check(@ModelAttribute BookingCheckerForm bookingCheckerForm) {


		List<Reserve> list = reserveRepository.findAllByCid(bookingCheckerForm.getCid());


		return list;
	}
}
