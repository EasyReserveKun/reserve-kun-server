package com.example.demo.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reserve;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

/**
 * ReserveService.java
 * 予約に関する処理を実装するクラス
 * @author のうみそ＠overload
 */
@Service
@AllArgsConstructor
public class ReserveService {

	private final ReserveRepository reserveRepository;

	
	/** 
	 * その時間にコンシェルジュが既に予約されていないかをチェックする
	 * @param reserve チェックする予約情報
	 * @return　既に予約されている場合はその予約情報、予約されていない場合はnull
	 */
	public Reserve findReserveDuplicated(Reserve reserve) {
		Optional<Reserve> optionalReserve = reserveRepository.findAllByDateAndTimeAndEid(
				reserve.getDate(), reserve.getTime(), reserve.getEid());
		if (optionalReserve.isEmpty()) {
			return null;
		} else {
			return optionalReserve.get();
		}
	}
	
	/** 
	 * その時間に顧客が既に予約を入れていないかチェックする
	 * @param reserve チェックする予約情報
	 * @return　既に予約している場合はその予約情報、予約されていない場合はnull
	 */
	public Reserve findReserveDoubled(Reserve reserve) {
		Optional<Reserve> optionalReserve = reserveRepository.findAllByDateAndTimeAndCid(
				reserve.getDate(), reserve.getTime(), reserve.getCid());
		if (optionalReserve.isEmpty()) {
			return null;
		} else {
			return optionalReserve.get();
		}
	}
	
	/** 
	 * その時間にコンシェルジュが既に予約されていないか、またその時間その顧客が既に予約を入れていないかチェックする
	 * @param reserve チェックする予約情報
	 * @return　コンシェルジュが埋まっていたらDuplicated、顧客が埋まっていたらDoubled、問題がなければ空文字
	 */
	public String checkReserveDuplicatedOrDoubled(Reserve reserve) {
		if(Objects.nonNull(findReserveDuplicated(reserve))) {
			return "Duplicated";
		}
		if(Objects.nonNull(findReserveDoubled(reserve))) {
			return "Doubled";
		}
		return "";
		
	}
}
