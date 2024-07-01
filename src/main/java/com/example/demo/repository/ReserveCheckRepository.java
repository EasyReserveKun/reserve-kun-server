package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reserve;
import com.example.demo.entity.ReserveCompositeKey;

public interface ReserveCheckRepository extends JpaRepository<Reserve, ReserveCompositeKey> {

	@Query(value = "SELECT time FROM t_reserve WHERE date = :date AND eid = :eid", nativeQuery = true)
	List<String> findAllTimesByDateAndEid(@Param("date") Date date, @Param("eid") String eid);

}
