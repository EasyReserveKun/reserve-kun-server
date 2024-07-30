package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Customer.java
 * 顧客情報テーブルと連携するEntityクラス
 * @author のうみそ＠overload
 */
@Data
@Entity
@Table(name = "m_customer")
public class Customer {

	/** 
	 * 顧客ID
	 * 実際にはメールアドレスが入力される
	 * 主キー　
	 */
    @Id
    @Column(name = "cid")
    public String cid;

    /**
     * 顧客名
     */
    @Column(name = "cname")
    public String cname;

    /**
     * パスワード
     */
    @Column(name = "password")
    public String password;
    
    /**
     * 管理者権限
     * String型で、管理者ならば1、そうでなければnull
     */
    @Column(name = "administrator")
    public String admin;

}
