package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.entity.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class EmployeeRestController {
	private final EmployeeRepository employeeRepository;

	@GetMapping("/api/getEmployee")
	public HashMap<String, Object> getEmployee(){
		HashMap<String, Object> responce = new HashMap<>();
		List<Employee> empList = employeeRepository.findAll();
		responce.put("results", empList);
		responce.put("type", "Employee");
		responce.put("status", "success");
		return responce;

	}
}
