package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//--------------------------------------------------//
//  Employee.java
//  コンシェルジュ情報を保持するエンティティクラス
//--------------------------------------------------//

@Data  // 基本的なメソッドの自動生成
@Entity  // エンティティクラスであることの宣言
@Table(name = "m_employee")  // "m_employee"テーブルの指定
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

}
