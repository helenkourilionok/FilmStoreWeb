package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceAuthException;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class LoginationCommand implements Command {

	private final static Logger logger = LogManager.getLogger(LoginationCommand.class);
	
	private final static String EMAIL = "email";
	private final static String PASSWORD = "password";
	private final static String ERROR_MESSAGE = "Incorrect password or email!";
	private final static String ERROR_ATTRIBUTE = "incorrectEmailOrPassword";
	private final static String DEFAULT_LANGUAGE = "ru";
	private final static String REMEMBER_ME = "remember_me";
		
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		boolean redirect = Boolean.parseBoolean(request.getParameter(CommandParamName.REDIRECT));
		
		if(redirect){
			
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			
		}else{
			
			String email = request.getParameter(EMAIL);
			String password = request.getParameter(PASSWORD);
			String rememberMe = request.getParameter(REMEMBER_ME);
			
			try {
				
				userAuth(email, password, session);
				
				saveEmailPassInCookie(response, rememberMe, email, password);
				
				String language = getValueFromCookies(request,CommandParamName.LANGUAGE);
				language = language == null ? DEFAULT_LANGUAGE : language;
				session.setAttribute(CommandParamName.LOCALE,language);
				
				String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
				if (prev_query != null) {
					
					response.sendRedirect(prev_query);
					
				} else {
					
					request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
					
				}
			} catch (FilmStoreServiceException e) {
				
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
				
			} catch (FilmStoreServiceAuthException e) {
				logger.error("Authorization failed!User with these email and password wasn't find!");
				request.setAttribute(ERROR_ATTRIBUTE,ERROR_MESSAGE);
				request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
				
			}
		}
		
	}

	private void userAuth(String email,String password,HttpSession httpSession) throws FilmStoreServiceException, FilmStoreServiceAuthException{
		
		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactoryImpl.getUserService();
		
		User user = userService.authorisation(email, password);
		
		httpSession.setAttribute(CommandParamName.USER_EMAIL, user.getEmail());
		httpSession.setAttribute(CommandParamName.USER_ROLE, user.getRole().name());

	}
	
	private void saveEmailPassInCookie(HttpServletResponse httpServletResponse,String remember_me,String email,String password){
		String remember = "true";
		String nameEmailCookie = "email";
		String namePassCookie = "password";
		if(remember.equals(remember)){
			httpServletResponse.addCookie(new Cookie(nameEmailCookie,email));
			httpServletResponse.addCookie(new Cookie(namePassCookie,password));
		}
	}
	
	private String getValueFromCookies(HttpServletRequest httpServletRequest,String cookieName) {
		Cookie[] listCookies = httpServletRequest.getCookies();
		String value = null;
		if(listCookies == null){
			return value;
		}
		for (Cookie cookie : listCookies) {
			if (cookie.getName().equals(cookieName)) {
				value = cookie.getValue();
			}
		}
		return value;
	}
}
