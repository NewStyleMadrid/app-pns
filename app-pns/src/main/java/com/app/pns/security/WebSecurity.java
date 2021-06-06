package com.app.pns.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.app.pns.security.jwt.JwtEntryPoint;
import com.app.pns.security.jwt.JwtTokenFilter;
import com.app.pns.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter  {

	 @Autowired
	    UserDetailsServiceImpl userDetailsServiceImpl;

	    @Autowired
	    JwtEntryPoint jwtEntryPoint;

	    @Bean
	    public JwtTokenFilter jwtTokenFilter(){
	        return new JwtTokenFilter();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    @Bean
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

	    @Override
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }

	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/auth/**").permitAll()
	                .antMatchers("/producto/lista").permitAll()
	                .antMatchers("/servicio/lista").permitAll()
	                //.antMatchers("/productos").permitAll()
	                .antMatchers("/home/**").permitAll()
	                .antMatchers("/corte/lista").permitAll() // Permitimos que la lista de diseños sea pública
	                .anyRequest().authenticated()
	                .and()
	                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
}
