package com.josephcolbert.ecommerce.security.jwt;

import com.josephcolbert.ecommerce.security.securityEntity.PrincipalUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
        List<String> roles = principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Jwts.builder().setSubject(principalUser.getUsername())
               .claim("roles", roles)
               .setIssuedAt(new Date())
               .setExpiration(new Date(new Date().getTime() + expiration * 1000))
               .signWith(getSecret(secret))
               .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
            return true;
        } catch(MalformedJwtException e) {
            logger.error("Token mal formado");
        } catch(UnsupportedJwtException e) {
            logger.error("Token no soportado");
        } catch(ExpiredJwtException e) {
            logger.error("Token expirado");
        } catch(IllegalArgumentException e) {
            logger.error("Token vacio");
        } catch(SignatureException e) {
            logger.error("Fail en la firma");
        }
        return false;
    }

    private Key getSecret(String secret) {
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}

