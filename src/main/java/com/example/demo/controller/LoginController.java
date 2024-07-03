package com.example.demo.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;

@RestController
public class LoginController {

    private final LoginService loginService;

    // LoginServiceをインジェクトするコンストラクタ
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // POSTメソッドで "/Login" パスにマッピングされるログイン処理のエンドポイント
    @PostMapping("/Login")
    public int login(@ModelAttribute LoginForm loginForm) {
        // LoginFormからユーザ名（cid）とパスワードを取得して、LoginServiceのcountメソッドを呼び出す
        return loginService.count(loginForm.getCid(), loginForm.getPassword());
    }
}
