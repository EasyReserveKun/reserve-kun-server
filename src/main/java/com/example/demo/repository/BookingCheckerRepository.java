package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reserve;
import com.example.demo.entity.ReserveCompositeKey;

public interface BookingCheckerRepository extends JpaRepository<Reserve,  ReserveCompositeKey> {
	public List<Reserve> findAllByCid(String cid);

}
