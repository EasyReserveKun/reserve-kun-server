package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
@Entity  // このクラスがJPAエンティティであることを示します
@Table(name = "m_customer")  // テーブル名が"m_customer"であることを指定します
public class Customer {

    @Id  // このフィールドが主キーであることを示します
    @Column(name = "cid")  // データベースのカラム名が"cid"であることを指定します
    public String cid;  // 顧客ID

    @Column(name = "cname")  // データベースのカラム名が"cname"であることを指定します
    public String cname;  // 顧客名

    @Column(name = "password")  // データベースのカラム名が"password"であることを指定します
    public String password;  // パスワード

}
