package com.example.demo.form;

import lombok.Data;

@Data  // 基本的なメソッドの自動生成
public class CustomerLoginForm {

    private String cid;  // 顧客ID
    private String password;  // パスワード

}
