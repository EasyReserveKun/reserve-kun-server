package com.example.demo.service;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.springframework.stereotype.Service;

@Service
public class EncodeService {
	public static boolean canEncodeToSJIS(String[] inputs) {
		CharsetEncoder encoder = Charset.forName("Shift_JIS").newEncoder();
		for (String input : inputs) {
			if (!encoder.canEncode(input)) {
				return false;
			}
		}
		return true;
	}
}