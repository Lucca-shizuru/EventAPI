package com.shizuru.EventApi.repositories;

import com.shizuru.EventApi.dtos.UserRecordDto;
import com.shizuru.EventApi.exceptions.UserNotFoundException;
import com.shizuru.EventApi.models.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    boolean existsByEmail(String email);

   default UserModel saveUser(UserModel userModel){
       return save(userModel);
   }
   default List<UserModel> findAllUsers(){
       return findAll();
   }
    default Optional<UserModel> getUserById(UUID userId) {
        return findById(userId);

   }
    default UserModel updateUser(UUID userId, UserRecordDto userRecordDto) {
        return findById(userId).map(existingUser -> {
            BeanUtils.copyProperties(userRecordDto, existingUser, "userId");
            return save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
   default void deleteUser(UserModel userModel){
       delete(userModel);
   }
   default long countUsers(){
       return count();
   }
}
