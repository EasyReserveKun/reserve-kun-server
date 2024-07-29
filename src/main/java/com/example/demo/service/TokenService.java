package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;


/**
 * tokenの生成や照合を行うメソッドを実装したクラス
 * @author のうみそ＠overload
 */
@Service
public class TokenService {

	private final CustomerRepository customerRepository;
	
    public TokenService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    private final SecretKey secret = Jwts.SIG.HS256.key().build();
    
    /**
     * トークンからユーザー名を抽出する
     * @param token JWTトークン
     * @return ユーザー名
     */
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
    	return (String) claims.getSubject();
    }

    /**
     * トークンからユーザーIDを抽出する
     * @param token JWTトークン
     * @return ユーザーID
     */
    public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
    	return (String) claims.get("userId");
    }
    
    /**
     * トークンから管理者フラグを抽出する
     * @param token JWTトークン
     * @return 管理者フラグ
     */
    public boolean extractIsAdmin(String token) {
        Claims claims = extractAllClaims(token);
        return (boolean) claims.get("isAdmin");
    }

    /**
     * トークンの有効期限を抽出する
     * @param token JWTトークン
     * @return 有効期限の日時
     */
    public Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return (Date) claims.getExpiration();
    }

    /**
     * トークンからすべてのクレームを抽出する
     * @param token JWTトークン
     * @return クレーム
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
        		.verifyWith(secret)
        		.build()
        		.parseSignedClaims(token)
        		.getPayload();
    }

    /**
     * トークンが期限切れかどうかをチェックする
     * @param token JWTトークン
     * @return 期限切れならtrue、そうでない場合はfalse
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * ユーザー名、ユーザーID、および管理者フラグを使ってトークンを生成する
     * @param username ユーザー名
     * @param userId ユーザーID
     * @param isAdmin 管理者フラグ
     * @return 生成されたJWTトークン
     */
    public String generateToken(String username, String userId, boolean isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("isAdmin", isAdmin);
        return createToken(claims, username);
    }

    /**
     * クレームと主題を使ってトークンを生成する
     * @param claims クレーム
     * @param subject 主題（通常はユーザー名）
     * @return 生成されたJWTトークン
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 48)) // 48時間の有効期間
                .signWith(secret)
                .compact();
    }

    /**
     * トークンが有効かどうかを検証する
     * @param token JWTトークン
     * @param username ユーザー名
     * @return トークンが有効ならtrue、そうでない場合はfalse
     */
    public Boolean validateToken(String token) {
    	String name = null;
    	String id = null;
    	
    	// 上手くトークンとして認識できなかったらfalse
    	try {
    		name = extractUsername(token);
    		id = extractUserId(token);
    	} catch (Exception e) {
    		return false;
    	}

    	// 該当する顧客情報が見つからなければfalse
    	Optional<Customer> customer = customerRepository.findByCnameAndCid(name , id);
        if(customer.isEmpty()) {
        	return false;
        }
        
        // トークンが期限切れならfalse
        if(isTokenExpired(token)) {
        	return false;
        }
        
        // 全て通過したらtrue
        return true;
    }
}
