package com.rubycon.rubyconteam2.domain.user.service;

import com.rubycon.rubyconteam2.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
}
