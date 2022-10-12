package com.questApp.security;

import io.jsonwebtoken.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenProvider {

    @Value("${questapp.app.secret}")
    String APP_SECRET;

    @Value("${questapp.expires.in}")
    Long EXPIRES_IN;


    public String generateJwtToken(Authentication authentication){
        JwtUserDetails jwtUserDetails= (JwtUserDetails) authentication.getPrincipal();

        Date date=new Date(new Date().getTime()+EXPIRES_IN);

        return Jwts.builder().setSubject(Long.toString(jwtUserDetails.getId()))
               .setIssuedAt(new Date()).setExpiration(date).signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    public Long getUserIdFromJwt(String token){
        Claims claims=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();

        return Long.parseLong(claims.getSubject());
    }

    private boolean isTokenExpired(String token){
        Date date=Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return date.before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
