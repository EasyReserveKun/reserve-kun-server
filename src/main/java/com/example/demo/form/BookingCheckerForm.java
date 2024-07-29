package com.example.demo.form;

import lombok.Data;

/**
 * 重複確認のため送られてきた従業員IDと顧客IDを保持するクラス
 * 保持するデータが1つしかないため、将来的に削除する予定です
 * @author のうみそ＠overload
 */
@Data
public class BookingCheckerForm {
	private String cid;  // 顧客ID

	private String eid;
}
