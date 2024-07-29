package com.example.demo.form;

import com.example.demo.entity.Customer;

import lombok.Data;

/**
 * CustomerForm.java
 * サインアップの際に使う情報を保持するためのクラス
 * @author のうみそ＠overload
 */
@Data
public class SignupForm {

    private String cid;  // 顧客ID
    private String cname;  // 顧客名
    private String password;  // パスワード


    /**
     * フォームに入力された情報をCustomer型に変換して返すメソッド
     * @return Customer型の登録情報
     */
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
