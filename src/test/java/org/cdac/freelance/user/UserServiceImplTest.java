package org.cdac.freelance.user;

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
import org.cdac.freelance.services.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private CreateUserRequestDTO createRequest;
    private Users user;

    @BeforeEach
    void setUp() {

        createRequest = new CreateUserRequestDTO();
        createRequest.setFullName("Vinit Joshi");
        createRequest.setUserName("vinit");
        createRequest.setPassword("Password@123");
        createRequest.setEmail("vinit@gmail.com");
        createRequest.setPhoneNo("9876543210");
        createRequest.setPortfolio("portfolio");

        user = new Users();
        user.setUserId(1);
        user.setFullName("Vinit Joshi");
        user.setUserName("vinit");
        user.setPassword("encodedPassword");
        user.setEmail("vinit@gmail.com");
        user.setPhoneNo("9876543210");
        user.setPortfolio("portfolio");
    }

    @Test
    void createUser_Success() {

        when(usersRepository.existsByUserName(any())).thenReturn(false);
        when(usersRepository.existsByEmail(any())).thenReturn(false);
        when(usersRepository.existsByPhoneNo(any())).thenReturn(false);

        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        boolean result = userService.createUser(createRequest);

        assertTrue(result);

        ArgumentCaptor<Users> captor = ArgumentCaptor.forClass(Users.class);
        verify(usersRepository).save(captor.capture());

        Users saved = captor.getValue();

        assertEquals("Vinit Joshi", saved.getFullName());
        assertEquals("vinit", saved.getUserName());
        assertEquals("encodedPassword", saved.getPassword());
        assertEquals("vinit@gmail.com", saved.getEmail());
        assertEquals("9876543210", saved.getPhoneNo());
        assertEquals("portfolio", saved.getPortfolio());
    }

    @Test
    void createUser_UserNameAlreadyExists() {

        when(usersRepository.existsByUserName("vinit")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(createRequest));

        verify(usersRepository, never()).save(any());
    }

    @Test
    void createUser_EmailAlreadyExists() {

        when(usersRepository.existsByUserName(any())).thenReturn(false);
        when(usersRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(createRequest));

        verify(usersRepository, never()).save(any());
    }

    @Test
    void createUser_PhoneAlreadyExists() {

        when(usersRepository.existsByUserName(any())).thenReturn(false);
        when(usersRepository.existsByEmail(any())).thenReturn(false);
        when(usersRepository.existsByPhoneNo(any())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(createRequest));

        verify(usersRepository, never()).save(any());
    }

    @Test
    void login_Success() {

        LoginRequestDTO login = new LoginRequestDTO();
        login.setUserName("vinit");
        login.setPassword("Password@123");

        CustomUserPrincipal principal = new CustomUserPrincipal(user);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(principal);

        when(jwtUtil.generateToken(principal)).thenReturn("jwt-token");

        when(usersRepository.findByUserName("vinit"))
                .thenReturn(Optional.of(user));

        LoginResponseDTO response = userService.login(login);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("vinit", response.getUserName());
        assertEquals("Vinit Joshi", response.getFullName());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken(principal);
    }

    @Test
    void login_UserNotFound() {

        LoginRequestDTO login = new LoginRequestDTO();
        login.setUserName("vinit");
        login.setPassword("Password@123");

        CustomUserPrincipal principal = new CustomUserPrincipal(user);

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(principal);

        when(jwtUtil.generateToken(principal))
                .thenReturn("jwt-token");

        when(usersRepository.findByUserName("vinit"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.login(login));
    }

    @Test
    void getUserByUserName_Success() {

        when(usersRepository.findByUserName("vinit"))
                .thenReturn(Optional.of(user));

        UserReposneDTO response =
                userService.getUserByUserName("vinit");

        assertNotNull(response);
        assertEquals(1, response.getUserId());
        assertEquals("Vinit Joshi", response.getFullName());
        assertEquals("vinit", response.getUserName());
        assertEquals("vinit@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getPhoneNo());
        assertEquals("portfolio", response.getPortfolio());
    }

    @Test
    void getUserByUserName_UserNotFound() {

        when(usersRepository.findByUserName("vinit"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByUserName("vinit"));
    }
}