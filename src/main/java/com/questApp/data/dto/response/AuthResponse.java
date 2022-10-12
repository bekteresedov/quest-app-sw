package com.questApp.data.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {

    String message;
    Long userId;
    String accessToken;
    String refreshToken;
}
