package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Temporary;
import com.example.demo.repository.TemporaryRepository;

import lombok.AllArgsConstructor;

/**
 * SignupService.java
 * 新規登録に関する機能を実装するクラス
 * @author のうみそ＠overload
 */
@Service
@AllArgsConstructor
public class SignupService {

	private final TemporaryRepository temporaryRepository;

	/**
	 * 認証コードが既に存在する他の仮登録データと重複していないかを確かめるメソッド
	 * @param uuid 確認対象の確認コード
	 * @return 重複していればtrue、重複していなければfalse
	 */
	// ユーザ名（cid）とパスワードを受け取り、LoginRepositoryを通じて該当するレコードの数を取得する
	public Boolean isNotUuidDuplicate(String uuid) {
		Optional<Temporary> optionalTemporary = temporaryRepository.findByUuid(uuid);
		return optionalTemporary.isEmpty();
	}

	/**
	 * 認証コードを基に仮登録情報を取得するメソッド
	 * @param uuid もとになる認証コード
	 * @return 一致するものがあれば仮登録情報、なければnullを返す
	 */
	public Temporary getCustomerFormFromUuid(String uuid) {
		Optional<Temporary> optionalTemporary = temporaryRepository.findByUuid(uuid);
		if(!optionalTemporary.isEmpty()) {
			Temporary temporaryCustomer = optionalTemporary.get();
			return temporaryCustomer;
		}
		return null;
	}
}
