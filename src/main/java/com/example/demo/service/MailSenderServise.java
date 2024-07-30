package com.example.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * MailSenderService.java
 * 存在するアカウントを確認するための機能を実装するクラス
 * @author のうみそ＠overload
 */
@Service
@AllArgsConstructor
public class MailSenderServise {

	private final JavaMailSender mailSender;

	@Async
	public void sendSimpleMessage(String to, String subject, String text) {
		var mail = new SimpleMailMessage();
		mail.setTo(to);
		mail.setFrom("mailuser_semi847@orange-piece2.sakura.ne.jp");
		mail.setSubject(subject);
		mail.setText(text);
		try {
			mailSender.send(mail);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
