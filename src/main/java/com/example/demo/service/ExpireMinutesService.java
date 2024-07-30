package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExpireMinutesService {

	@Value("${expire.minutes:3}")
	private String expireMinutes;

	public String getCodeExpiryMinutesString() {
		return expireMinutes;
	}

	public int getCodeExpiryMinutes() {
		return Integer.parseInt(expireMinutes);
	}
}