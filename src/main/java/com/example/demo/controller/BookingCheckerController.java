package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Reserve;
import com.example.demo.form.BookingCheckerForm;
import com.example.demo.repository.BookingCheckerRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController

public class BookingCheckerController {
	
	private final BookingCheckerRepository bookingCheckerRepository;
	@PostMapping("/Booking")
	public List <Reserve> bookingChecker(@ModelAttribute BookingCheckerForm bookingCheckerForm) {
		List<Reserve> list = bookingCheckerRepository.findAllByCid(
				bookingCheckerForm.getCid()
				);
		return list;
		
		
	}
	

}
