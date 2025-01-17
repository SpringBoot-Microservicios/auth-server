package com.debuggeandoideas.authserver.services;

import com.debuggeandoideas.authserver.dtos.TokenDto;
import com.debuggeandoideas.authserver.dtos.UserDto;
import com.debuggeandoideas.authserver.entities.UserEntity;
import com.debuggeandoideas.authserver.helpers.JwtHelper;
import com.debuggeandoideas.authserver.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Transactional
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_EXCEPTION_MESSAGE = "Error to auth user";
    private final JwtHelper jwtHelper;


    @Override
    public TokenDto login(UserDto userDto) {
        final var userFromDB = this.userRepository.findByUsername(userDto.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE));
        this.validPassword(userDto, userFromDB);
        return TokenDto
                .builder()
                    .accessToken(this.jwtHelper.createToken(userFromDB.getUsername()))
                .build();
    }

    @Override
    public TokenDto validateToken(TokenDto token) {
        if (this.jwtHelper.validateToken(token.getAccessToken())){
            return TokenDto
                    .builder()
                        .accessToken(token.getAccessToken())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE);
    }

    private void validPassword(UserDto userDto, UserEntity userEntity) {
        if(!this.passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, USER_EXCEPTION_MESSAGE);
        }
    }

}
