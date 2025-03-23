package com.shizuru.EventApi.services;

import com.shizuru.EventApi.dtos.UserRecordDto;
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

    public UserModel createUser(UserModel user){
        return userRepository.save(user);
    }
    public List<UserModel> getallUsers(){
        return userRepository.findAll();
    }
    public Optional<UserModel> getUserById(UUID userId){
        return userRepository.findAllById(userId);
    }
    public UserModel updateUser(UUID userId, @Valid UserRecordDto userRecordDto) {
        Optional<UserModel> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserModel userModel = existingUser.get();

            BeanUtils.copyProperties(userRecordDto, userModel, "userid");

            return userRepository.save(userModel);
        }
        return null;
    }
    public boolean deleteUser(UUID userId){
        if (userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }




}
