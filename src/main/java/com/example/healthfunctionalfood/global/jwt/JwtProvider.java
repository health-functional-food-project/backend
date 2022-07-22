package com.example.healthfunctionalfood.global.jwt;

import com.example.healthfunctionalfood.dto.UserResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider implements InitializingBean {

    private String AUTHORITIES_KEY = "healthFunctionalFood";
    private String secret = "DSQADSAJHBVDFDHWQBDLWQJKHBDLWQJKHDGWLQJHKDGWQLJHDGWQKJLKHVBDLKSAJHFDSLAKJFHDSALKJFDHSALKFJDSAHFLKDSAJHFDLSKAJFHDSALKFJDSHALFKDSJAHFLDKSAJFHDLSAKFJDHSALKFDSUJAHFLDKSAJHFDFDLHJVBFDSL";
    private long tokenValidityInMilliseconds = 100000000000000000l;
    private long accessTokenValidityInMilliseconds = 100000000000000000l;
    private long refreshTokenValidityInMilliseconds = 100000000000000000l;
    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(UserResponseDto.KakaoUserDto kakaoUserDtoForJwtToken) {

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .setSubject(kakaoUserDtoForJwtToken.getKakaoId())
                .claim(AUTHORITIES_KEY, kakaoUserDtoForJwtToken)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .compact();

        return accessToken = "Bearer " + accessToken;
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토근 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
