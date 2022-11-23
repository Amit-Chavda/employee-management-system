package com.springbootapp.emlpoyee.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.springbootapp.emlpoyee.aspects.LoggingAspect;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	final private Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			LOG.warn("User : " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
		}
		String msg = "You don't have permission to access this URL!";
		response.sendRedirect(request.getContextPath() + "/AccessDenied");
	}

}
