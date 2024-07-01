package com.example.demo.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.CustomerForm;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    // POSTメソッドでの処理を定義する
    @PostMapping("/c")
    public String add(@ModelAttribute CustomerForm customerForm) {
        
        // CustomerRepositoryを通じてデータベースに顧客情報を挿入する
        customerRepository.insertUser(customerForm.getCid(), customerForm.getCname(), customerForm.getPassword());
        
        // レスポンスとして"NewFile"を返す
        return "NewFile";
    }

}
