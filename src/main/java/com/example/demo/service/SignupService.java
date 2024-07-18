package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Temporary;
import com.example.demo.form.CustomerForm;
import com.example.demo.repository.TemporaryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SignupService {

	private final TemporaryRepository temporaryRepository;

	// ユーザ名（cid）とパスワードを受け取り、LoginRepositoryを通じて該当するレコードの数を取得する
	public Boolean isNotUuidDuplicate(String uuid) {
		Optional<Temporary> optionalTemporary = temporaryRepository.findByUuid(uuid);
		return optionalTemporary.isEmpty();
	}

	public Temporary getCustomerFormFromUuid(String uuid) {
		Optional<Temporary> optionalTemporary = temporaryRepository.findByUuid(uuid);
		if(!optionalTemporary.isEmpty()) {
			Temporary temporaryCustomer = optionalTemporary.get();
			return temporaryCustomer;
		}
		return null;
	}
}
