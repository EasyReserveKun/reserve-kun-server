package com.example.demo.service;

import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class ResponceService {
	public static HashMap<String, Object> responceMaker(String status){
		HashMap<String, Object> responce = new HashMap<>();
		responce.put("status", status);
		return responce;
	}
}
