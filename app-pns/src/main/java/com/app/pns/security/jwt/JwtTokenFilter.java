package com.app.pns.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import com.app.pns.service.UserDetailsServiceImpl;

public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getToken(request);
			if (token != null && jwtProvider.validateToken(token)) {
				String nombreUsuario = jwtProvider.getUserNameFromToken(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(nombreUsuario);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			logger.error("fail en método doFilter " + e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String authReq = request.getHeader("Authorization");
		if (authReq != null && authReq.startsWith("Bearer "))
			return authReq.replace("Bearer ", "");
		return null;
	}

}
