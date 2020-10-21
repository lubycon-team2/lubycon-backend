package com.rubycon.rubyconteam2.domain.user.service;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    /**
     * User Phone Number 업데이트 메서드
     */
    @Transactional
    public User update(Long userId, NCPVerifyRequest ncpVerifyRequest){
        String phoneNumber = ncpVerifyRequest.getTo();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.updatePhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    /**
     * User 검색
     */
    @Transactional
    public User findById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }
}
