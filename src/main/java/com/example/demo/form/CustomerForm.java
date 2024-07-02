package com.example.demo.form;

import com.example.demo.entity.Customer;

import lombok.Data;

@Data  // Lombokの@Dataアノテーション：ゲッター、セッター、toString、equals、hashCodeなどを自動生成します
public class CustomerForm {

    private String cid;  // 顧客ID

    private String cname;  // 顧客名

    private String password;  // パスワード

    public Customer getEntity() {
        // エンティティオブジェクトを取得するメソッド

        Customer customer = new Customer();  // Customerエンティティのインスタンスを作成します
        customer.setCid(cid);  // 顧客IDを設定します
        customer.setCname(cname);  // 顧客名を設定します
        customer.setPassword(password);  // パスワードを設定します
        return customer;  // 作成したCustomerエンティティオブジェクトを返します
    }
}
