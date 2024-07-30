package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpireMinutesConfig {

	@Value("${expire.minutes:3}")
	private String expireMinutes;

	public String getCodeExpiryMinutesString() {
		return expireMinutes;
	}

	public int getCodeExpiryMinutes() {
		return Integer.parseInt(expireMinutes);
	}
}