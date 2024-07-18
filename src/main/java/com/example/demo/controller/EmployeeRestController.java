package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.ReserveService;
import com.example.demo.service.ResponceService;

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
	private final ReserveRepository reserveRepository;
	private final ReserveService reserveService;


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
    @CrossOrigin
	@PostMapping("stop")
	public HashMap<String, Object> stop(@RequestBody ReserveForm reserveForm){
    	
    	HashMap<String, Object> responce = new HashMap<>();
    	Reserve reserve=reserveForm.insertEntity();
    	
    	String error = reserveService.reserveExceptionCheck(reserveForm);
        if(error.isEmpty()) {
            // データベースへの書き込み
            reserveRepository.saveAndFlush(reserve);
            responce = ResponceService.responceMaker("Success");
        } else {
        	responce = ResponceService.responceMaker(error);
        }

		return responce;
    }
}
