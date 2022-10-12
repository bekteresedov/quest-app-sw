package com.questApp.service;

import com.questApp.data.entity.RefreshToken;
import com.questApp.data.entity.User;

public interface RefreshTokenService {

    public String createRefreshToken(User user);

    public boolean isRefreshExpired(RefreshToken token);

    public RefreshToken getByUser(Long userId);
}
