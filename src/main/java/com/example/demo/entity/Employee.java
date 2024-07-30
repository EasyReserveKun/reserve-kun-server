package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Employee.java
 * コンシェルジュ情報テーブルと連携するEntityクラス
 * @author のうみそ＠overload
 */
@Data
@Entity
@Table(name = "m_employee")
public class Employee {

    /**
     * コンシェルジュID
     * 実際にはindexが振られる
     * 主キー
     */
    @Id
    @Column(name = "eid")
    public Integer eid;

    /**
     * コンシェルジュの指名
     */
    @Column(name = "ename")
    public String ename;

    /**
     * コンシェルジュの相談カテゴリ
     */
    @Column(name = "category")
    public String category;
    
    /**
     * 予約停止フラグ
     * String型で、停止されていれば1、そうでなければnull
     */
    @Column(name = "stop_flag")
    public String stop_flag;

}
