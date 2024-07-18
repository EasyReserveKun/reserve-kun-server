package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Customer;

// JpaRepositoryを拡張したCustomerエンティティ用のリポジトリインターフェース
public interface AdminRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT * FROM m_customer WHERE cid = :cid AND password = crypt(:password, password)", nativeQuery = true)
    public Optional<Customer> findByCidAndPassword(String cid, String password);

}
