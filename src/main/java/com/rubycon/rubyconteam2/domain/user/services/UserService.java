package com.rubycon.rubyconteam2.domain.user.services;

import com.rubycon.rubyconteam2.domain.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
}
