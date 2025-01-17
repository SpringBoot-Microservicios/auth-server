package com.debuggeandoideas.authserver.services;

import com.debuggeandoideas.authserver.dtos.TokenDto;
import com.debuggeandoideas.authserver.dtos.UserDto;

public interface AuthService {

    TokenDto login(UserDto userDto);

    TokenDto validateToken(TokenDto token);


}
