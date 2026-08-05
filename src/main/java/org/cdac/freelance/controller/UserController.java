package org.cdac.freelance.controller;

import org.cdac.freelance.dto.user.CreateUserRequestDTO;
import org.cdac.freelance.dto.user.LoginRequestDTO;
import org.cdac.freelance.dto.user.LoginResponseDTO;
import org.cdac.freelance.dto.user.UserReposneDTO;
import org.cdac.freelance.security.CustomUserPrincipal;
import org.cdac.freelance.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequestDTO userRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @PostMapping("/login")
    public  ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(userService.login(loginRequestDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserReposneDTO> getUserByUsername(@AuthenticationPrincipal CustomUserPrincipal user){
        return ResponseEntity.ok(userService.getUserByUserName(user.getUsername()));
    }
}
