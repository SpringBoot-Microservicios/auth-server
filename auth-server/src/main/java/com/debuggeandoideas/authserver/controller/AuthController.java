package com.debuggeandoideas.authserver.controller;

import com.debuggeandoideas.authserver.dtos.TokenDto;
import com.debuggeandoideas.authserver.dtos.UserDto;
import com.debuggeandoideas.authserver.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path="login")//password secret
    public ResponseEntity<TokenDto>jwtCreate(@RequestBody UserDto userDto ) {
        return ResponseEntity.ok(this.authService.login(userDto));
    }


    @PostMapping(path="jwt")
    public ResponseEntity<TokenDto>jwtValidate(@RequestHeader String accessToken ) {
        return ResponseEntity.ok(this.authService.validateToken(TokenDto.builder().accessToken(accessToken).build()));
    }
}
