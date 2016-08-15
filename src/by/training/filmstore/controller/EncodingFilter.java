package by.training.filmstore.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter{

	private String encoding;
	private final static String ENCODING_PARAM = "encoding";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		encoding = filterConfig.getInitParameter(ENCODING_PARAM);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestEncoding = request.getCharacterEncoding();
		if(encoding!=null && !encoding.equalsIgnoreCase(requestEncoding)){
			request.setCharacterEncoding(encoding);
			response.setCharacterEncoding(encoding);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
		encoding = null;
		
	}
}
