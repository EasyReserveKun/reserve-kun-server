package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * ExpireMinutesService.java
 * 仮登録情報の有効期限を定義するクラス
 * @author のうみそ＠overload
 */
@Service
public class ExpireMinutesService {

	@Value("${expire.minutes:3}")
	private String expireMinutes;

	/**
	 * 設定ファイルに設定されている有効期限(分)をStringで返す。設定されていなければデフォルトで3分。
	 * @return String型の有効期限(分)
	 */
	public String getCodeExpiryMinutesString() {
		return expireMinutes;
	}

	/**
	 * 設定ファイルに設定されている有効期限(分)をStringで返す。設定されていなければデフォルトで3分。
	 * @return String型の有効期限(分)
	 */
	public int getCodeExpiryMinutes() {
		return Integer.parseInt(expireMinutes);
	}
}