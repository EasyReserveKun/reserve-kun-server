package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
@Entity  // このクラスがJPAエンティティであることを示します
@Table(name = "m_employee")  // テーブル名が"m_employee"であることを指定します
public class Employee {

    @Id  // このフィールドが主キーであることを示します
    @Column(name = "eid")  // データベースのカラム名が"eid"であることを指定します
    public Integer eid;  // 従業員ID

    @Column(name = "ename")  // データベースのカラム名が"ename"であることを指定します
    public String ename;  // 従業員名

    @Column(name = "category")  // データベースのカラム名が"category"であることを指定します
    public String category;  // カテゴリ

}
