package com.questApp.service.impl;

import com.questApp.data.entity.RefreshToken;
import com.questApp.data.entity.User;
import com.questApp.data.repository.RefreshTokenRepository;
import com.questApp.service.RefreshTokenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    final RefreshTokenRepository refreshTokenRepository;


    @Override
    public String createRefreshToken(User user) {

        RefreshToken refreshToken=refreshTokenRepository.findByUserId(user.getId());
        if (refreshToken==null){
            refreshToken=new RefreshToken();
            refreshToken.setUser(user);
        }
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    @Override
    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
