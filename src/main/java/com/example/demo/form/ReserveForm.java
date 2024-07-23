package com.example.demo.form;

import java.sql.Date;

import com.example.demo.entity.Reserve;

import lombok.Data;

@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
public class ReserveForm {

    private Date date;  // 予約日

    private String time;  // 予約時間

    private String eid;  // 従業員ID

    private String cid;  // 顧客情報

    private String etc;  // その他の情報

    // Reserveクラスへデータの受け渡し
    public Reserve getEntity() {
        // Reserveエンティティオブジェクトを取得するメソッド

        Reserve reserve = new Reserve();  // Reserveエンティティのインスタンスを作成します
        reserve.setDate(date);  // 予約日を設定します
        reserve.setTime(time);  // 予約時間を設定します
        reserve.setEid(eid);  // 従業員IDを設定します
        reserve.setCid(cid);  // 顧客IDを設定します
        reserve.setEtc(etc);  // その他の情報を設定します
        return reserve;  // 作成したReserveエンティティオブジェクトを返します
    }
    
    public Reserve insertEntity() {
        // Reserveエンティティオブジェクトを取得するメソッド

        Reserve reserve = new Reserve();  // Reserveエンティティのインスタンスを作成します
        reserve.setDate(date);  // 予約日を設定します
        reserve.setTime(time);  // 予約時間を設定します
        reserve.setEid(eid);  // 従業員IDを設定します
        reserve.setCid(cid);  // 顧客IDを設定します
        reserve.setEtc("予約停止中");  // その他の情報を設定します
        reserve.setStop_flag("1");

        return reserve;  // 作成したReserveエンティティオブジェクトを返します
    }
}
