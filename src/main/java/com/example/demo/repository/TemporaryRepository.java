package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Temporary;

// JpaRepositoryを拡張したCustomerエンティティ用のリポジトリインターフェース
public interface TemporaryRepository extends JpaRepository<Temporary, String> {
	
    @Modifying
    @Query(value = "INSERT INTO m_temporary (cid, cname, password, date, uuid) " +
                   "VALUES (?1, ?2, crypt(?3, gen_salt('bf')), ?4, ?5) " +
                   "ON CONFLICT (cid) DO UPDATE SET " +
                   "cname = EXCLUDED.cname, " +
                   "password = EXCLUDED.password, " +
                   "date = EXCLUDED.date, " +
                   "uuid = EXCLUDED.uuid",
           nativeQuery = true)
    public void generateTemp(String cid, String cname, String password, LocalDateTime date, String uuid);
    
    @Modifying
    @Query(value = "DELETE FROM m_temporary "
    		+ "WHERE date + INTERVAL '1 minutes'*?1 < NOW()", nativeQuery = true)
    public void deleteExpire(int expire);

	
    public Optional<Temporary> findByUuid(String uuid);
}
