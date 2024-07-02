package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reserve;
import com.example.demo.entity.ReserveCompositeKey;

// Reserveエンティティ用のリポジトリインターフェース
public interface ReserveRepository extends JpaRepository<Reserve, ReserveCompositeKey> {

}
