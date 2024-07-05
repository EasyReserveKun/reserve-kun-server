package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

//--------------------------------------------------//
//　　EmployeeRestController.java
//　　コンシェルジュに関する情報を提供するコントローラークラス
//--------------------------------------------------//

@AllArgsConstructor
@RestController
@RequestMapping("/employee")
public class EmployeeRestController {
	private final EmployeeRepository employeeRepository;

    @CrossOrigin
	@GetMapping("list")
	public HashMap<String, Object> list(){
		HashMap<String, Object> responce = new HashMap<>();
		List<Employee> empList = employeeRepository.findAll();
		responce.put("results", empList);
		responce.put("type", "Employee");
		responce.put("status", "success");
		return responce;

	}
}
