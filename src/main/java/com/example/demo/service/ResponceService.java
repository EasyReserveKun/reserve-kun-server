package com.example.demo.service;

import java.util.HashMap;
import org.springframework.stereotype.Service;

/**
 * ReserveService.java
 * POSTのresponceのテンプレートを実装するクラス
 * @author のうみそ＠overload
 */
@Service
public class ResponceService {
	
	
	/**
	 * ステータスの中に任意に設定するメソッド
	 * @param status ステータスの中の入れる文字列。UpperCamelCaseを推奨します。
	 * @return statusに引数の文字列が入ったHashMap
	 */
	public static HashMap<String, Object> statusCustom(String status){
		HashMap<String, Object> responce = new HashMap<>();
		responce.put("status", status);
		return responce;
	}
	
	/**
	 * ステータスの中にSuccessを入れるメソッド
	 * @return status:Successが入ったHashMap
	 */
	public static HashMap<String, Object> statusSuccess(){
		HashMap<String, Object> responce = new HashMap<>();
		responce.put("status", "Success");
		return responce;
	}
	
	/**
	 * ステータスの中にSuccessを入れるメソッド
	 * @return status:Errorが入ったHashMap
	 */
	public static HashMap<String, Object> statusError(){
		HashMap<String, Object> responce = new HashMap<>();
		responce.put("status", "Error");
		return responce;
	}
}
