package com.dayalbagh.epay;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.core.annotation.Order;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


//@WebFilter(filterName = "DisableEtagFilter" ,urlPatterns = "/*" )
//@Component
public class DisableEtagFilter  implements Filter  {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	
		HttpServletRequest req = (HttpServletRequest)request;
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-Security-Policy", "default-src 'unsafe-inline' 'self';style-src "
        		+ "  'unsafe-inline'  http://localhost:8080/   https://admission.dei.ac.in;img-src http://* 'self' data: ;connect-src 'self' https://test.sbiepay.sbi https://sbiepay.sbi"
        		+ ";font-src 'self' data:");
		
	 System.out.println(req.getHeader("User-Agent"));
		
		chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) httpResponse) {
			 public void setHeader(String name, String value) {
		          if (!"etag".equalsIgnoreCase(name)) {
		              super.setHeader(name, value);
		          }
		      }
			
		});
		
		
	}

	
	
		
		
		
	
		
	
}
