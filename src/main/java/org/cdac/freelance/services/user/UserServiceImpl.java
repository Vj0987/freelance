package org.cdac.freelance.services.user;

import org.cdac.freelance.dto.user.CreateUserRequestDTO;
import org.cdac.freelance.dto.user.LoginRequestDTO;
import org.cdac.freelance.dto.user.LoginResponseDTO;
import org.cdac.freelance.dto.user.UserReposneDTO;
import org.cdac.freelance.entity.Users;
import org.cdac.freelance.exceptions.EmailAlreadyExistsException;
import org.cdac.freelance.exceptions.UserAlreadyExistsException;
import org.cdac.freelance.exceptions.UserNotFoundException;
import org.cdac.freelance.repository.UsersRepository;
import org.cdac.freelance.security.CustomUserPrincipal;
import org.cdac.freelance.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public boolean createUser(CreateUserRequestDTO userRequestDTO) {
        if (usersRepository.existsByUserName(userRequestDTO.getUserName())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (usersRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (usersRepository.existsByPhoneNo(userRequestDTO.getPhoneNo())) {
            throw new UserAlreadyExistsException("Phone number already exists");
        }

        Users user = new Users();

        user.setFullName(userRequestDTO.getFullName());
        user.setUserName(userRequestDTO.getUserName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhoneNo(userRequestDTO.getPhoneNo());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPortfolio(userRequestDTO.getPortfolio());

        usersRepository.save(user);
        return true;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUserName(),
                        loginRequestDTO.getPassword()
                )
        );

        CustomUserPrincipal userPrincipal =
                (CustomUserPrincipal) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userPrincipal);

        Users user = usersRepository.findByUserName(userPrincipal.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        LoginResponseDTO response = new LoginResponseDTO();

        response.setToken(token);
        response.setUserName(userPrincipal.getUsername());
        response.setFullName(userPrincipal.getUser().getFullName());
        return  response;
    }

    @Override
    public UserReposneDTO getUserByUserName(String userName) {
        Users user = usersRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("User not found"));

        UserReposneDTO responseDTO = new UserReposneDTO();

        responseDTO.setUserId(user.getUserId());
        responseDTO.setFullName(user.getFullName());
        responseDTO.setUserName(user.getUserName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhoneNo(user.getPhoneNo());
        responseDTO.setPortfolio(user.getPortfolio());

        return responseDTO;
    }
}
