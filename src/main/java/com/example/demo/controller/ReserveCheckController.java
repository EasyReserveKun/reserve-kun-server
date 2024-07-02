package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.ReserveCheckForm;
import com.example.demo.repository.ReserveCheckRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor  // Lombokのアノテーション：全ての引数を持つコンストラクタを生成します
@RestController  // このクラスがRESTコントローラであることを示します
public class ReserveCheckController {

    private final ReserveCheckRepository reserveCheckRepository;  // 自動ワイヤリングされるリポジトリの依存関係

    @PostMapping("/b")  // "/b"エンドポイントに対するPOSTリクエストを処理します
    public List<String> reserveCheck(@ModelAttribute ReserveCheckForm reserveCheckForm) {
        // @ModelAttributeでバインディングされたHTTP POSTリクエストを処理するメソッドです

        // 日付と社員IDに基づいてリポジトリから時間のリストを取得します
        List<String> list = reserveCheckRepository.findAllTimesByDateAndEid(
            reserveCheckForm.getDate(),  // フォームオブジェクトから日付を取得します
            reserveCheckForm.getEid()    // フォームオブジェクトから社員IDを取得します
        );
        
        return list;  // 時間のリストをレスポンスとして返します
    }
}
