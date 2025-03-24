package com.shizuru.EventApi.services;

import com.shizuru.EventApi.dtos.UserRecordDto;
import com.shizuru.EventApi.exceptions.EventIsFullException;
import com.shizuru.EventApi.exceptions.UserNotFoundException;
import com.shizuru.EventApi.models.UserModel;
import com.shizuru.EventApi.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    private static final int maxUsers = 10;

    public UserModel createUser(@Valid UserRecordDto userRecordDto) {
        long usercount = userRepository.count();
        if (usercount >= maxUsers) {
            throw new EventIsFullException("Event is full, if anyone leaves, we contact you");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        return userRepository.save(userModel);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(UUID userId) {
        return userRepository.findAllById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserModel updateUser(UUID userId, @Valid UserRecordDto userRecordDto) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        BeanUtils.copyProperties(userRecordDto, userModel, "userid");

        return userRepository.save(userModel);
    }


    public boolean deleteUser(UUID userId) {
        UserModel userModel = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        userRepository.delete(userModel);
        return true;
    }


}
