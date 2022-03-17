package com.springbootapp.emlpoyee.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springbootapp.emlpoyee.helper.CustomAccessDeniedHandler;
import com.springbootapp.emlpoyee.service.EmployeeDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private EmployeeDetailsService employeeDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		http
		
		.authorizeHttpRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/Login").permitAll()
		.antMatchers("/Admin/**").hasRole("ADMIN")
		.antMatchers("/Employees/**").hasRole("USER");
		
	http	
		.formLogin()
		.loginPage("/Login")
		.usernameParameter("email")
		.failureUrl("/LoginError")
		.successHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
					redirectStrategy.sendRedirect(request, response,"Admin/");
				}else {
					redirectStrategy.sendRedirect(request, response,"Employees/Home?email="+authentication.getName());
				}	
			}
		});
		
		
	http
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.clearAuthentication(true)
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true)
		.logoutSuccessUrl("/");
	
	http
		.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());

	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(employeeDetailsService);
		return authenticationProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
