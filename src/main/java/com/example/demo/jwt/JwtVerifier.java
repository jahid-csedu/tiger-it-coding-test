package com.example.demo.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtVerifier extends OncePerRequestFilter {
	

	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;

	public JwtVerifier(JwtConfig jwtConfig, SecretKey secretKey) {
		super();
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		
		if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
		
		try {
			
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			
			Claims body = jwsClaims.getBody();
			String username = body.getSubject();
			List<Map<String, String>> authorities = (List<Map<String, String>>)body.get("authorities");
			Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
																		.map(m -> new SimpleGrantedAuthority(m.get("authority")))
																		.collect(Collectors.toSet());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (JwtException e) {
			throw new IllegalStateException("Invalid Token");
		}
		
		filterChain.doFilter(request, response);

	}

}
