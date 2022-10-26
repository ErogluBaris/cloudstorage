package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.SignupFormModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final HashService hashService;
    private final UserMapper userMapper;

    public UserService(HashService hashService,
                       UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public int createUser(SignupFormModel signupFormModel) {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(signupFormModel.getPassword(), encodedSalt);
        return userMapper.insert(new User(null, signupFormModel.getUsername(), encodedSalt, hashedPassword, signupFormModel.getFirstName(), signupFormModel.getLastName()));
    }

    public boolean isUsernameAvailable(String username) {
        User user = userMapper.getUser(username);

        return user == null;
    }

    public User findAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userMapper.getUser(authentication.getName());
    }
}
