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

	// 従業員ID (主キー)
    @Id
    @Column(name = "eid")
    public Integer eid;

    // 従業員名
    @Column(name = "ename")
    public String ename;

    // カテゴリ
    @Column(name = "category")
    public String category;
    
    @Column(name = "stop_flag")
    public String stop_flag;

}
