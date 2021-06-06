package com.app.pns.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.app.pns.dto.JwtDTO;
import com.app.pns.security.UsuarioPrincipal;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.*;

@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

	// Anotaciones declaradas en application properties

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	// Fin

	public String generateToken(Authentication authentication) {
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		return Jwts.builder()
				.setSubject(usuarioPrincipal.getUsername())
				.setIssuedAt(new Date())
				.claim("roles", roles)
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public String getUserNameFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token mal creado!" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado!" + e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Token expirado!" + e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Token vacio!" + e.getMessage());
		}
		/*
		 * catch (SignatureException e) { logger.error("error en la firma " +
		 * e.getMessage()); }
		 */
		return false;
	}
	/*
	public String refreshTkn( JwtDTO jwtDTO) throws ParseException {
		
		JWT jwt= JWTParser.parse(jwtDTO.getToken());
		JWTClaimsSet claim=jwt.getJWTClaimsSet(); // Caracteristicas del tkn
		String userName=claim.getSubject();
		@SuppressWarnings("unchecked")
		List<String> roles= (List<String>)claim.getClaim("roles");
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.claim("roles", roles)
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}
	*/
}
