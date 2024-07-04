package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

//--------------------------------------------------//
//　　ReserveRestController.java
//　　予約情報のキーを保持する埋め込みクラス
//--------------------------------------------------//

@Data  // 基本的なメソッドの自動生成
@Embeddable  // このクラスをCompositeKey用のクラスとして扱う
@Table(name = "t_reserve")  // "t_reserve"テーブルの指定
public class ReserveCompositeKey {

	// 予約日
    @Column(name = "date")
    private Date date;

    // 予約時間
    @Column(name = "time")
    private String time;

    // 従業員ID
    @Column(name = "eid")
    private String eid;
}
