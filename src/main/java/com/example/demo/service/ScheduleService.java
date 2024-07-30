package com.example.demo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.repository.TemporaryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleService {
	
	private final TemporaryRepository temporaryRepository;
	private final ExpireMinutesService emService;
	
	@Transactional
	@Scheduled(cron = "0 0,30 * * * *")
   public void scheduledDeleteExpire() {
		System.out.println("失効した仮登録情報を削除しました");
       temporaryRepository.deleteExpire(emService.getCodeExpiryMinutes());
   }

}
