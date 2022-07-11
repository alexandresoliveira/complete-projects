package dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import dev.aleoliv.thezueranetwork.thezueraneverendsapp.usecases.v1.security.authentication.dtos.AppUserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

  @Value("${jwt.secret}")
  private String secret;

  public UUID getIdFromToken(String token) {
    if (token.contains("Bearer ")) {
      String removeBearer = token.substring(7);
      String id = getClaimFromToken(removeBearer, Claims::getSubject);
      return UUID.fromString(id);
    }
    String id = getClaimFromToken(token, Claims::getSubject);
    return UUID.fromString(id);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  private Boolean isValidId(UUID id, UserDetails userDetails) {
    return id.equals(((AppUserDTO) userDetails).getId());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, ((AppUserDTO) userDetails).getId().toString());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    return (isValidId(getIdFromToken(token), userDetails) && !isTokenExpired(token));
  }

  public String getTokenWith(String headerBearer) {
    if (headerBearer.contains("Bearer ")) {
      return headerBearer.substring(7);
    }
    return "";
  }
}
