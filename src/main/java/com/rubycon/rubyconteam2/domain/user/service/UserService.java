package com.rubycon.rubyconteam2.domain.user.service;

import com.rubycon.rubyconteam2.domain.user.domain.User;
import com.rubycon.rubyconteam2.domain.user.exception.UserNotFoundException;
import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import com.rubycon.rubyconteam2.infra.sms.dto.NCPVerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    /**
     * User Phone Number 업데이트 메서드
     * TODO: 로그인 한 사용자만 사용 할 수 있도록 수정 ~!
     * 임시로 id 받는 것
     */
    public User update(NCPVerifyRequest ncpVerifyRequest){
        String phoneNumber = ncpVerifyRequest.getTo();

        User user = userRepository.findById(1L) // 임시로 1번 유저만
                .orElseThrow(UserNotFoundException::new);

        user.updatePhoneNumber(phoneNumber);
        return userRepository.save(user);
    }
}
