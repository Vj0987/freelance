package org.cdac.freelance.services.user;

import org.cdac.freelance.dto.user.CreateUserRequestDTO;
import org.cdac.freelance.dto.user.LoginRequestDTO;
import org.cdac.freelance.dto.user.LoginResponseDTO;

public interface UserService {

     boolean createUser(CreateUserRequestDTO userRequestDTO);
     LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
