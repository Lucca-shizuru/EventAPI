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
public class UserService {

    private final UserRepository userRepository;

    private static final int maxUsers = 10;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel createUser(@Valid UserRecordDto userRecordDto) {
        if (userRepository.count() >= maxUsers) {
            throw new EventIsFullException("Event is full, if anyone leaves, we contact you");
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        return userRepository.saveUser(userModel);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public UserModel getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public UserModel updateUser(UUID userId, @Valid UserRecordDto userRecordDto) {
        return userRepository.updateUser(userId, userRecordDto);
    }


    public void deleteUser(UUID userId) {
        UserModel userModel = getUserById(userId);
        userRepository.deleteUser(userModel);

    }



}
