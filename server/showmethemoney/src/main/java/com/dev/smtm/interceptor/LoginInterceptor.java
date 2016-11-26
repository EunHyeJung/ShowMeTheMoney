package com.dev.smtm.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		try {
			if(request.getSession().getAttribute("user_id")==null){
				response.sendRedirect("/");
				logger.info("Á¦¹ß!");
				return false;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

}
