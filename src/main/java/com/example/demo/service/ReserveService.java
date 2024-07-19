package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reserve;
import com.example.demo.form.ReserveForm;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReserveService {

	private final ReserveRepository reserveRepository;

	public String reserveExceptionCheck(ReserveForm reserveForm) {
		Optional<Reserve> duplicated = reserveRepository.findAllByDateAndTimeAndEid(
				reserveForm.getDate(), reserveForm.getTime(), reserveForm.getEid());
		Optional<Reserve> doubled = reserveRepository.findAllByDateAndTimeAndCid(
				reserveForm.getDate(), reserveForm.getTime(), reserveForm.getCid());

		if (!duplicated.isEmpty()) {
			return "Duplicated";
		}
		if (!doubled.isEmpty()) {
			return "Doubled";
		}
		return "";
	}
}
