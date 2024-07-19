package com.example.demo.form;

import java.sql.Date;

import lombok.Data;

@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
public class ReserveCheckForm {

    private Date date;  // 予約日

    private String eid;  // 従業員ID
    
    private String stop_flag;

}
