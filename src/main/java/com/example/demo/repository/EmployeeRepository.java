package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Employee;

// JpaRepositoryを拡張したEmployeeエンティティ用のリポジトリインターフェース
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
