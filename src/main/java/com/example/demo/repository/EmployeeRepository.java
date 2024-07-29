package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Employee;

// JpaRepositoryを拡張したEmployeeエンティティ用のリポジトリインターフェース
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Modifying
    @Query(value="UPDATE m_employee SET stop_flag = '1' WHERE eid = :eid",nativeQuery = true)
    int updateSetFlag(@Param("eid") String eid);
	
	@Modifying
    @Query(value="UPDATE m_employee SET stop_flag = NULL WHERE eid = :eid",nativeQuery = true)
    int updateDeleteFlag(@Param("eid") String eid);
	
	@Query(value="SELECT COUNT(*) FROM m_employee where eid = :eid AND stop_flag = '1'",nativeQuery = true)
	int findAllByEid(@Param("eid") String eid);
}
