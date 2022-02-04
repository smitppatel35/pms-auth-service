package com.pms.auth.utils;

import com.pms.auth.model.MyUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JWTUtils {

    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    // SPEL -> spring expression language
    @Value("${jwt.secret}")
    private String SECRET;

    //generate token for user
    public String createToken(MyUserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", ((List) userDetails.getAuthorities()).get(0));

        return Jwts.builder()
                .setHeaderParam("token", "jwt")
                .setClaims(claims)
                .setSubject(userDetails.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

    // retrieve username from jwt token
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    // retrieve expiration date from jwt token
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //for retrieveing any information from token we will need the secret key
    public Claims extractAllClaims(String token){
        try {
            return Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        } catch (ExpiredJwtException ex){
            throw ex;
        }
    }

    //validate token
    public Boolean validateToken(String token, MyUserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getId()) && !isTokenExpired(token));
    }
}
