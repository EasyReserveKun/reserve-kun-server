package com.example.demo.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reserve;
import com.example.demo.entity.ReserveCompositeKey;

//--------------------------------------------------//
//  ReserveRepository.java
//  Reserve情報を扱う為のリポジトリクラス
//--------------------------------------------------//

public interface ReserveRepository extends JpaRepository<Reserve, ReserveCompositeKey> {

	// 予約されている時間を日程と相談内容でフィルタして検索
    @Query(value = "SELECT time FROM t_reserve WHERE date = :date AND eid = :eid", nativeQuery = true)
    List<String> findAllTimesByDateAndEid(@Param("date") Date date, @Param("eid") String eid);

	// 予約情報をユーザーでフィルタして検索
	public List<Reserve> findAllByCidOrderByDate(String cid);

	// 予約情報をコンシェルジュ、日付、時間でソートして検索
	public Optional<Reserve> findAllByDateAndTimeAndEid(Date date, String time, String eid);

	// 予約情報を顧客、日付、時間でソートして検索
	public Optional<Reserve> findAllByDateAndTimeAndCid(Date date, String time, String eid);
	
	public List<Reserve> findByDateAndTimeAndEid(Date date,String time,String eid);

}
