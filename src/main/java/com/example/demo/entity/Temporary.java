package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//--------------------------------------------------//
//　 Temporary.java
//　　仮登録情報を保持するエンティティクラス
//--------------------------------------------------//

@Data  // 基本的なメソッドの自動生成
@Entity  // エンティティクラスであることの宣言
@Table(name = "m_temporary")  // "m_temporary"テーブルの指定
public class Temporary {

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
    
    // 仮登録日時
    @Column(name = "date")
    public LocalDateTime date;
    
    // 仮登録コード
    @Column(name = "uuid")
    public String uuid;

}
