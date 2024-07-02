package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable  // reserveクラスのCompositeKeyクラスであることを示します
@Table(name = "t_reserve")  // テーブル名が"t_reserve"であることを指定します
@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
public class ReserveCompositeKey {

    @Column(name = "date")  // データベースのカラム名が"date"であることを指定します
    private Date date;  // 予約日

    @Column(name = "time")  // データベースのカラム名が"time"であることを指定します
    private String time;  // 予約時間

    @Column(name = "eid")  // データベースのカラム名が"eid"であることを指定します
    private String eid;  // 従業員ID
}
