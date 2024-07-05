package com.example.demo.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.form.CustomerForm;
import com.example.demo.form.CustomerLoginForm;
import com.example.demo.repository.CustomerRepository;

import lombok.AllArgsConstructor;

@Service
public class ResponceService {
	public static HashMap<String, Object> responceMaker(String status){
		HashMap<String, Object> responce = new HashMap<>();
		responce.put("status", status);
		return responce;
	}
}
