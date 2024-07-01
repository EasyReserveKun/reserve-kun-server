package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Table(name = "t_reserve")
@Data
public class ReserveCompositeKey {

	@Column(name = "date")
	private Date date;

	@Column(name = "time")
	private String time;

	@Column(name = "eid")
	private String eid;
}
