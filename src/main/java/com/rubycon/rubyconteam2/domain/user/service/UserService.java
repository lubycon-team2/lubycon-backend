package com.rubycon.rubyconteam2.domain.user.service;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.infra.sms.dto.request.NCPVerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    /**
     * User Phone Number 업데이트 메서드
     */
    public User update(Long userId, NCPVerifyRequest ncpVerifyRequest){
        String phoneNumber = ncpVerifyRequest.getTo();

        User user = userRepository.findById(userId) // 임시로 1번 유저만
                .orElseThrow(UserNotFoundException::new);

        user.updatePhoneNumber(phoneNumber);
        return userRepository.save(user);
    }
}
