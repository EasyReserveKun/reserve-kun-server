package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.CustomerForm;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.LoginService;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//  CustomerRestController.java
//  顧客に関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    private final CustomerRepository customerRepository;
    private final LoginService loginService;

    // 送信されたアカウント情報の照合を行うAPI
    @CrossOrigin
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody CustomerLoginForm customerLoginForm) {
    	String loginName = loginService.isAccountExist(customerLoginForm);
    	HashMap<String, Object> responce = new HashMap<>();
    	responce.put("result", loginName);
    	return responce;
    }

    // POSTメソッドでの処理を定義する
    @PostMapping("/signup")
    public String signup(@ModelAttribute CustomerForm customerForm) {

        // DBにユーザー情報を登録
        customerRepository.insertUser(customerForm.getCid(), customerForm.getCname(), customerForm.getPassword());

        // ページを返す
        //TODO: NewFileは仮ページのため、正式なリダイレクトを実装する
        return "NewFile";
    }

}
