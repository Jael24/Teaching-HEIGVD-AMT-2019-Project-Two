package ch.heigvd.amt.project.two.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// Source: https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world

@Component
public class JwtTokenUtil implements Serializable {
    //private static final long serialVersionUID = -2550185165626007488L;

    //@Value("${jwt.secret}")
    private static String secret = "guillaumejael";

    //retrieve email from jwt token
    public static String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve isBlocked from jwt token
    public static Boolean getIsBlockedFromToken(String token) {
        return getAllClaimsFromToken(token).get("isBlocked", Boolean.class);
    }

    //retrieve isAdmin from jwt token
    public static Boolean getIsAdminFromToken(String token) {
        return getAllClaimsFromToken(token).get("isAdmin", Boolean.class);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}