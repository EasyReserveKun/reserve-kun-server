package com.example.demo.service;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.springframework.stereotype.Service;

/**
 * EncodeService.java
 * 文字コードの確認を行うクラス
 * @author のうみそ＠overload
 */
@Service
public class EncodeService {
	
	/**
	 * 配列内の全ての文字列がSJISにエンコードできるか確認する
	 * @param 確認を行いたい文字の配列
	 * @return SJISにエンコード可能ならtrue、そうでなければfalse
	 */
	public static boolean canEncodeToSJIS(String[] inputs) {
		CharsetEncoder encoder = Charset.forName("Shift_JIS").newEncoder();
		for (String input : inputs) {
			if (!encoder.canEncode(input)) {
				return false;
			}
		}
		return true;
	}
}