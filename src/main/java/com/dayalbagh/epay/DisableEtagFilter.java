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

@Component 
@Order(1)
public class DisableEtagFilter  implements Filter  {

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
	
		
		chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) response) {
			 public void setHeader(String name, String value) {
		          if (!"etag".equalsIgnoreCase(name)) {
		              super.setHeader(name, value);
		          }
		      }
			
		});
		
		
	}

	
	
		
		
		
	
		
	
}
