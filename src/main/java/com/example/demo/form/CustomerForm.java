package com.example.demo.form;

import com.example.demo.entity.Customer;

import lombok.Data;

@Data  // 基本的なメソッドの自動生成
public class CustomerForm {

    private String cid;  // 顧客ID
    private String cname;  // 顧客名
    private String password;  // パスワード


    // エンティティオブジェクトを取得するメソッド
    public Customer getEntity() {
    	// 戻し値となるcustomerオブジェクト
        Customer customer = new Customer();

        // customerオブジェクトにFormに入力された値を代入
        customer.setCid(cid);
        customer.setCname(cname);
        customer.setPassword(password);

        //フォーム情報通りのcustomerを返す
        return customer;
    }
}
