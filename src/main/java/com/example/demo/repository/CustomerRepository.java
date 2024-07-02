package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Customer;

import jakarta.transaction.Transactional;

// JpaRepositoryを拡張したCustomerエンティティ用のリポジトリインターフェース
public interface CustomerRepository extends JpaRepository<Customer, String> {

    // ネイティブクエリを使用して、新しいユーザーを挿入するためのメソッド
    @Modifying  // 更新クエリであることを示します
    @Transactional  // トランザクションを管理することを示します
    @Query(value = "INSERT INTO m_customer (cid, cname, password) VALUES (?1, ?2, crypt(?3, gen_salt('bf')))", nativeQuery = true)
    void insertUser(String cid, String cname, String password);
}
