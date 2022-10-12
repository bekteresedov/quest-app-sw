package com.questApp.controller;
import com.questApp.data.dto.request.RefreshRequest;
import com.questApp.data.dto.request.UserLogin;
import com.questApp.data.dto.request.UserRegister;
import com.questApp.data.dto.response.AuthResponse;
import com.questApp.data.entity.RefreshToken;
import com.questApp.data.entity.User;
import com.questApp.security.JwtTokenProvider;
import com.questApp.service.RefreshTokenService;
import com.questApp.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthController {

    final AuthenticationManager authenticationManager;

    final JwtTokenProvider jwtTokenProvider;

    final UserService userService;

    final BCryptPasswordEncoder bCryptPasswordEncoder;

    final RefreshTokenService refreshTokenService;


    @PostMapping("/login")
    public  AuthResponse login(@RequestBody UserLogin userLogin){

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(userLogin.getUserName(), userLogin.getPassword());

        Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenProvider.generateJwtToken(authentication);

        User user=userService.getOneUserByUserName(userLogin.getUserName());

        AuthResponse authResponse=new AuthResponse();
        authResponse.setAccessToken("Bearer " +token);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse>register(@RequestBody UserRegister userRegister){
        AuthResponse authResponse=new AuthResponse();
        if (userService.getOneUserByUserName(userRegister.getUserName())!=null){
            authResponse.setMessage("Username already in use.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }else {
            User user=new User();
            user.setUserName(userRegister.getUserName());
            user.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
            userService.saveOneUser(user);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                    new UsernamePasswordAuthenticationToken(userRegister.getUserName(), userRegister.getPassword());

            Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token=jwtTokenProvider.generateJwtToken(authentication);

            authResponse.setMessage("User successfully registered.");
            authResponse.setRefreshToken("Bearer " + token);
            authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
            authResponse.setUserId(user.getId());
            return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
        }
    }

    @PostMapping("/refresh")

    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        AuthResponse authResponse=new AuthResponse();
        RefreshToken refreshToken=refreshTokenService.getByUser(refreshRequest.getUserId());

        if(refreshToken.getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(refreshToken)) {
            User user=refreshToken.getUser();

            String token= jwtTokenProvider.generateJwtTokenByUserId(user.getId());

            authResponse.setMessage("token successfully refreshed.");
            authResponse.setAccessToken("Bearer " + token);
            authResponse.setUserId(user.getId());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }else {
            authResponse.setMessage("refresh token is not valid.");
            return new ResponseEntity<>(authResponse,HttpStatus.UNAUTHORIZED);
        }


    }
}
