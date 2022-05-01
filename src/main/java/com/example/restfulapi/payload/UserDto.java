package com.example.restfulapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Email(message = "You entered an email incorrectly")
    private String email;

    @Size(min = 8, message = "Password should not be less than 8 characters")
    private String password;
}
