package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Temporary;

import jakarta.transaction.Transactional;

// JpaRepositoryを拡張したCustomerエンティティ用のリポジトリインターフェース
public interface TemporaryRepository extends JpaRepository<Temporary, String> {
	
    @Modifying
    @Transactional 
    @Query(value = "INSERT INTO m_temporary (cid, cname, password, date, uuid) VALUES (?1, ?2, crypt(?3, gen_salt('bf')), ?4, ?5)", nativeQuery = true)
    public void insertUser(String cid, String cname, String password, LocalDateTime date, String uuid);
	
    public Optional<Temporary> findByUuid(String uuid);
}
