package com.example.demo.form;

import java.sql.Date;

import lombok.Data;

/**
 * 予約確認のため送られてきた情報を保持するフォームクラス
 * @author のうみそ＠overload
 */
@Data
public class ReserveCheckForm {

    private Date date;  // 予約日

    private String eid;  // 従業員ID
    
    private String stopFlag; //管理者側で入れた予約停止情報を区別するためのフラグ

}
