package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * ReserveCompositeKey.java
 * 予約情報テーブルの複合キーを扱うEntityクラス
 * @author のうみそ＠overload
 */
@Data 
@Embeddable
@Table(name = "t_reserve")
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
