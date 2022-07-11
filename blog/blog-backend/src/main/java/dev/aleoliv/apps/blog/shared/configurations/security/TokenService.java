package dev.aleoliv.apps.blog.shared.configurations.security;

import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import dev.aleoliv.apps.blog.shared.database.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	@Value("${blog.jwt.secret}")
	private String secret;
	
	@Value("${blog.jwt.expiration}")
	private Long expiration;
	
	@Value("${blog.jwt.issuer}")
	private String issuer;

	public String buildToken(Authentication authenticate) {
		UserEntity userEntity = (UserEntity) authenticate.getPrincipal();
		Date today = new Date();
		Date expirationDate = new Date(today.getTime() + expiration);
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		return Jwts.builder()
					.setIssuer(issuer)
					.setSubject(userEntity.getId().toString())
					.setIssuedAt(today)
					.setExpiration(expirationDate)
					.signWith(secretKey, SignatureAlgorithm.HS256)
					.compact();
	}

	public boolean isValidToken(String token) {
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
		        .requireIssuer(issuer)
		        .build()
		        .parse(token);
			return true;
		} catch (Exception e) {}
		return false;
	}
	
	public UUID recoverUserId(String token) throws Exception {
		if (token.contains("Bearer "))
			token = token.substring(7);
		
		SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		
		try {
			Claims body = (Claims) Jwts.parserBuilder()
				.setSigningKey(secretKey)
		        .requireIssuer(issuer)
		        .build()
		        .parse(token)
		        .getBody();
			return UUID.fromString(body.getSubject());
		} catch (Exception e) {
			throw new Exception("BadRequest - Subject token not found", e);
		}
	}
}
