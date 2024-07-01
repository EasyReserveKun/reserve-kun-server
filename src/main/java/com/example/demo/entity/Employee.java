package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_employee")
public class Employee {
	@Id
	@Column(name = "eid")
	public Integer eid;

	@Column(name = "ename")
	public String ename;

	@Column(name = "category")
	public String category;
}
