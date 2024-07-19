package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//--------------------------------------------------//
//　 Customer.java
//　　顧客情報を保持するエンティティクラス
//--------------------------------------------------//

@Data  // 基本的なメソッドの自動生成
@Entity  // エンティティクラスであることの宣言
@Table(name = "m_customer")  // "m_customer"テーブルの指定
public class Customer {

	// 顧客ID (主キー)
    @Id
    @Column(name = "cid")
    public String cid;

    // 顧客名
    @Column(name = "cname")
    public String cname;

    // パスワード
    @Column(name = "password")
    public String password;
    
    // 
    @Column(name = "administrator")
    public String admin;

}
