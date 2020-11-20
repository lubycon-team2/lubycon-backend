package com.rubycon.rubyconteam2.infra.sms.repository;

import com.rubycon.rubyconteam2.infra.sms.domain.NCPAuthCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NCPAuthCodeRepository extends CrudRepository<NCPAuthCode, String> {
}
