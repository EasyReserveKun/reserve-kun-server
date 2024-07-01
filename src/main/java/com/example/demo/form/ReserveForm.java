package com.example.demo.form;

import java.sql.Date;

import com.example.demo.entity.Reserve;

import lombok.Data;

@Data
public class ReserveForm {

	private Date date;

	private String time;

	private String eid;

	private String cid;

	private String etc;

	public Reserve getEntity() {

		Reserve reserve = new Reserve();
		reserve.setDate(date);
		reserve.setTime(time);
		reserve.setEid(eid);
		reserve.setCid(cid);
		reserve.setEtc(etc);
		return reserve;

	}
}