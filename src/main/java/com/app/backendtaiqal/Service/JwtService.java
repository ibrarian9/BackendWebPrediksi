package com.app.backendtaiqal.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpire;

    public String generateJwtToken(Authentication auth) {

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsers().getNama())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpire))
                .claim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList().get(0))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e){
            log.error("Invalid JWT Token : {} ", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is Expired : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported : {} ", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("Jwt Claims string is empty : {} ", e.getMessage());
        }
        return false;
    }
}
