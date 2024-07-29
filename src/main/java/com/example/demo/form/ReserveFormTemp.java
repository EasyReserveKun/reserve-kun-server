
package com.example.demo.form;

import java.sql.Date;

import com.example.demo.entity.Reserve;

import lombok.Data;

/**
 * 予約情報を保持するフォームクラス
 * アカウント情報をtokenで扱うようになったため対応のために一時的に使用しています
 * 将来的に削除する予定です
 * @deprecated
 * @author のうみそ＠overload
 */
@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
public class ReserveFormTemp {

    private Date date;  // 予約日

    private String time;  // 予約時間

    private String eid;  // 従業員ID

    private String token;  // 顧客情報

    private String etc;  // その他の情報
    
    
    /**
     * token以外の情報をReserveがたに挿入して返すメソッド
     * @return Reserve型の予約情報
     */
    public Reserve getEntity() {
        Reserve reserve = new Reserve();  // Reserveエンティティのインスタンスを作成します
        reserve.setDate(date);  // 予約日を設定します
        reserve.setTime(time);  // 予約時間を設定します
        reserve.setEid(eid);  // 従業員IDを設定します
        reserve.setEtc(etc);  // その他の情報を設定します
        return reserve;  // 作成したReserveエンティティオブジェクトを返します
    }

}
