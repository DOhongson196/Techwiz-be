package com.nosz.projectsem2be.security.jwt;

import com.nosz.projectsem2be.security.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private  static  final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private String jwtSecret = "dohongson196@gmail.com";
    private long jwtExpiration = 86400 * 1000;
    public String createToken(Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrinciple.getEmail())
                .setIssuedAt(new Date())
                .claim("roles",userPrinciple.getAuthorities().toString())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            logger.error("Invalid JWT signature -> Message: {}",e);
        } catch (MalformedJwtException e){
            logger.error("Invalid format Token -> Message: {}",e);
        } catch (ExpiredJwtException e){
            logger.error("Expired JWT token -> Message: {}",e);
        } catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token -> Message: {}",e);
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty --> Message {}",e);
        }
        return false;
    }

    public String getEmailFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

//    public String getSubject(String token) {
//        return parseClaims(token).getSubject();
//    }
//
//    public Claims parseClaims(String token){
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
