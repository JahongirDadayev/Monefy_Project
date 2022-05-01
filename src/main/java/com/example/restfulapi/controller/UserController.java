package com.example.restfulapi.controller;

import com.example.restfulapi.entity.DbUser;
import com.example.restfulapi.payload.UserDto;
import com.example.restfulapi.payload.result.ApiResponse;
import com.example.restfulapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<DbUser>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DbUser> getUser(@PathVariable Long id) {
        DbUser user = userService.getUser(id);
        return (user != null) ? ResponseEntity.status(HttpStatus.OK).body(user) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postUser(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.postUser(userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.updateUser(id, userDto);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        ApiResponse apiResponse = userService.deleteUser(id);
        return (apiResponse.isSuccess()) ? ResponseEntity.status(HttpStatus.OK).body(apiResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
