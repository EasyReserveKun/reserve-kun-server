package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

// t_reserveテーブルのエンティティクラス
@Data  // Lombokの@Dataアノテーションにより、ゲッター、セッター、toStringなどが自動生成されます
@Entity  // JPAのエンティティであることを示すアノテーション
@IdClass(ReserveCompositeKey.class)  // 複合キークラスを指定します
@Table(name = "t_reserve")  // マッピングするデータベースのテーブル名を指定します
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
}
