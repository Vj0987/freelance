package org.cdac.freelance.services.user;

import org.cdac.freelance.dto.user.CreateUserRequestDTO;
import org.cdac.freelance.dto.user.LoginRequestDTO;
import org.cdac.freelance.dto.user.LoginResponseDTO;
import org.cdac.freelance.dto.user.UserReposneDTO;

public interface UserService {

     boolean createUser(CreateUserRequestDTO userRequestDTO);
     LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
     UserReposneDTO getUserByUserName(String userName);
}
