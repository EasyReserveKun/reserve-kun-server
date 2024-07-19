package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.repository.TemporaryRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleService {
	
	private final TemporaryRepository temporaryRepository;
	
	@Value("${code.expire.minutes}")
    private final String expireMinutes;
	
	@Scheduled(cron = "0 0,30 * * * *")
    public void scheduledDeleteExpire() {
        temporaryRepository.deleteExpire(expireMinutes);
    }
}
