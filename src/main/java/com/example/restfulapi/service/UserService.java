package com.example.restfulapi.service;

import com.example.restfulapi.entity.DbUser;
import com.example.restfulapi.payload.UserDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<DbUser> getUsers() {
        return userRepository.findAll();
    }

    public DbUser getUser(Long id) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        return optionalDbUser.orElse(null);
    }

    public ApiResponse postUser(UserDto userDto) {
        if (!userRepository.existsByEmail(userDto.getEmail())) {
            return saveUser(new DbUser(), userDto, "Saved user information");
        } else {
            return new ApiResponse("There is a user you are entering", null, false);
        }
    }

    public ApiResponse updateUser(Long id, UserDto userDto) {
        Optional<DbUser> optionalDbUser = userRepository.findById(id);
        if (optionalDbUser.isPresent()) {
            if (!userRepository.existsByEmailAndIdNot(userDto.getEmail(), id)) {
                return saveUser(optionalDbUser.get(), userDto, "Update user information");
            } else {
                return new ApiResponse("There is a user you are entering", null, false);
            }
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }

    public ApiResponse deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);
                return new ApiResponse("Delete user information", null, true);
            } catch (Exception e) {
                e.printStackTrace();
                return new ApiResponse("Unable to delete this category due to errors", null, false);
            }
        } else {
            return new ApiResponse("No user matching the id you entered", null, false);
        }
    }

    private ApiResponse saveUser(DbUser user, UserDto userDto, String message) {
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResponse(message, user, true);
    }
}
