package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Customer;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer,String>{

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO m_customer (cid, cname, password) VALUES (?1, ?2, crypt(?3, gen_salt('bf')))", nativeQuery = true)
	void insertUser(String cid, String cname, String password);


}
