package com.example.Employee.TestAssignment.service.impl;

import com.example.Employee.TestAssignment.exceptions.CustomException;
import com.example.Employee.TestAssignment.models.RequestDto.UserCreateDto;
import com.example.Employee.TestAssignment.models.entity.User;
import com.example.Employee.TestAssignment.repository.UserRepository;
import com.example.Employee.TestAssignment.service.UserService;
import com.example.Employee.TestAssignment.utils.CatchableErrors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public String registerNewUser(UserCreateDto createDto) {

        User checkUserName = userRepository.findByUsername(createDto.getUsername());
        User checkEmail = userRepository.findByEmail(createDto.getEmail());


        if(Objects.nonNull(checkUserName)){
            throw new CustomException(CatchableErrors.USER_NAME_ALREADY_EXIST);
        }

        if(Objects.nonNull(checkEmail)){
            throw new CustomException(CatchableErrors.USER_EMAIL_ALREADY_REGISTERED);
        }


        User user = new User();
        user.setEmail(createDto.getEmail());
        user.setUsername(createDto.getUsername());
        user.setPassword(passwordEncoder.encode(createDto.getUsername()+"_passKey"));
        user.setRole(createDto.getRole());
        user.setStatus(true);

        user = userRepository.save(user);

        return "Registered";
    }

}
/**
 *  user name ::  email == username
 *
 *  password =: username+"_passkey"
 *
 */