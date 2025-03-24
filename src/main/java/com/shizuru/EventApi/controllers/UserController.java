package com.shizuru.EventApi.controllers;

import com.shizuru.EventApi.dtos.UserRecordDto;
import com.shizuru.EventApi.models.UserModel;
import com.shizuru.EventApi.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/EventApi")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserModel userModel = userService.createUser(userRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);

    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        UserModel userModel = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id,
                                             @RequestBody @Valid UserRecordDto userRecordDto) {
        UserModel userModel = userService.updateUser(id, userRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
