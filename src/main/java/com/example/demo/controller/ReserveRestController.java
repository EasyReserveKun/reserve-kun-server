package com.example.demo.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

// 予約操作のコントローラークラス
@AllArgsConstructor  // コンストラクタインジェクションを利用するためのLombokのアノテーション
@RestController  // RESTコントローラーとして動作することを示すアノテーション
public class ReserveRestController {

    private final ReserveRepository reserveRepository;  // ReserveRepositoryのインスタンスを保持するフィールド

    // POSTリクエストを処理し、予約をデータベースに書き込むメソッド
    @PostMapping("/reserve")  // POSTリクエストを"/reserve"エンドポイントにマッピング
    public RedirectView insert(@ModelAttribute ReserveForm reserveForm) {
        Reserve reserve = reserveForm.getEntity();  // ReserveFormからReserveエンティティを取得します
        reserveRepository.saveAndFlush(reserve);  // Reserveエンティティをデータベースに保存します

        // ホームへリダイレクトするRedirectViewを作成して返します
        return new RedirectView("https://easy-reserve-kun.azurewebsites.net/");
    }
}
