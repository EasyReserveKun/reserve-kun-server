package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.form.ReserveCheckForm;
import com.example.demo.repository.ReserveCheckRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ReserveCheckController {

	private final ReserveCheckRepository reserveCheckRepository;

	@PostMapping("/b")
	public List<String> reserveCheck(@ModelAttribute ReserveCheckForm reserveCheckForm) {

		List<String> list = reserveCheckRepository.findAllTimesByDateAndEid(reserveCheckForm.getDate(),
				reserveCheckForm.getEid());
		
		return list;

	}
}
