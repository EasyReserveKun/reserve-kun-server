package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.LoginRepository;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    // LoginRepositoryをインジェクトするコンストラクタ
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // ユーザ名（cid）とパスワードを受け取り、LoginRepositoryを通じて該当するレコードの数を取得する
    public int count(String cid, String password) {
        int count = loginRepository.countByUsernameAndPassword(cid, password);
        // レコードの数が1以上であればその数を、そうでなければ0を返す
        return count > 0 ? count : 0;
    }
}
