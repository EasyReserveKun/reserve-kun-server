package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

//--------------------------------------------------//
//  Reserve.java
//  予約情報を保持するエンティティクラス
//--------------------------------------------------//

@Data  // 基本的なメソッドの自動生成
@Entity  // エンティティクラスであることの宣言
@IdClass(ReserveCompositeKey.class)  // 複合キーの指定
@Table(name = "t_reserve")  // "t_reserve"テーブルの指定
public class Reserve {

    // 日付(主キー)
    @Id
    @Column(name = "date")
    private Date date;

    // 時間(主キー)
    @Id
    @Column(name = "time")
    private String time;

    // 従業員番号(主キー、外部キー)
    @Id
    @Column(name = "eid")
    private String eid;

    // 顧客番号(外部キー)
    @Column(name = "cid")
    private String cid;

    // 備考欄
    @Column(name = "etc")
    private String etc;
    
    @Column(name = "stop_flag")
    private String stopFlag;
}
