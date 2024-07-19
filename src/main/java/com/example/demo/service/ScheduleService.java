package com.example.demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.config.ExpireMinutesConfig;
import com.example.demo.repository.TemporaryRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleService {
	
	private final TemporaryRepository temporaryRepository;
	private final ExpireMinutesConfig emConfig;
	
	@Scheduled(cron = "0 0,30 * * * *")
    public void scheduledDeleteExpire() {
        temporaryRepository.deleteExpire(emConfig.getCodeExpiryMinutesString());
    }
}
