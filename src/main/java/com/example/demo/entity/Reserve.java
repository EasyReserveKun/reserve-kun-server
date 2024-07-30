package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Reserve.java
 * 予約情報テーブルと連携するEntityクラス
 * @author のうみそ＠overload
 */
@Data
@Entity  // エンティティクラスであることの宣言
@IdClass(ReserveCompositeKey.class)
@Table(name = "t_reserve")
public class Reserve {

    /**
     * 日付
     * Date型
     * 主キー
     */
    @Id
    @Column(name = "date")
    private Date date;

    /**
     * 時間
     * 主キー
     */
    @Id
    @Column(name = "time")
    private String time;

    /**
     * 従業員番号
     * 外部キー
     */
    @Id
    @Column(name = "eid")
    private String eid;

    /**
     * 顧客番号
     * 外部キー
     */
    @Column(name = "cid")
    private String cid;

    /**
     * 備考欄
     * 予約の際、顧客に自由に入力してもらう
     */
    @Column(name = "etc")
    private String etc;
    
    /**
     * 予約停止フラグ
     * String型で、停止なら1、そうでなければnullが入力される
     */
    @Column(name = "stop_flag")
    private String stopFlag;
}
