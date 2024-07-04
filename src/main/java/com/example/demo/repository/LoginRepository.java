package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Login;

public interface LoginRepository extends JpaRepository<Login, String>{
	 // ユーザ名とパスワードでレコードをカウントするカスタムクエリ
	@Query(value = "SELECT count(*) FROM m_customer WHERE cid = :cid AND password = crypt(:password, password)", nativeQuery = true)
	int countByUsernameAndPassword(@Param("cid") String cid, @Param("password") String password);
}
