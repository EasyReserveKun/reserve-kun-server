package com.example.demo.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class ReserveRestController {

	private final ReserveRepository reserveRepository;
	
	@PostMapping("/reserve")
	public String insert(@ModelAttribute ReserveForm reserveForm) {
		System.out.println("きてまーす");
		Reserve reserve =reserveForm.getEntity();
		reserveRepository.saveAndFlush(reserve);
		
		return "NewFile";
	}
}
