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
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;

public class SignUpCommand implements Command {

	private final static Logger logger = LogManager.getLogger(SignUpCommand.class);

	private final static String EMAIL = "email";
	private final static String PASSWORD = "password";
	private final static String COPY_PASSWORD = "copypassword";
	private final static String LAST_NAME = "last_name";
	private final static String FIRST_NAME = "first_name";
	private final static String PATRONIMIC = "patronimic";
	private final static String PHONE = "phone";
	private final static String BALANCE = "balance";
	private final static String DEFAULT_LANGUAGE = "ru";
	private final static String SIGN_UP_FAILED = "signUpFailed";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		
		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactoryImpl.getUserService();

		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String copyPassword = request.getParameter(COPY_PASSWORD);
		String lastName = request.getParameter(LAST_NAME);
		String firstName = request.getParameter(FIRST_NAME);
		String patronimic = request.getParameter(PATRONIMIC);
		String phone = request.getParameter(PHONE);
		String balance = request.getParameter(BALANCE);
		String language = (String)session.getAttribute(CommandParamName.LOCALE);
		language = language == null ? DEFAULT_LANGUAGE : language;
		
		try {
			User user = userService.create(email, password, copyPassword, lastName,
					firstName, patronimic, phone,balance);
			Cookie localeCookie = new Cookie(CommandParamName.LOCALE,language);
			localeCookie.setMaxAge(CommandParamName.MAX_AGE_COOKIE);
			response.addCookie(localeCookie);
			session.setAttribute(CommandParamName.USER_EMAIL, user.getEmail());
			session.setAttribute(CommandParamName.USER_ROLE, user.getRole());
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectUserParamException e) {
			logger.error("Validation user attributes failed!", e);
			request.setAttribute(SIGN_UP_FAILED,"true");
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_SIGNUP).forward(request, response);
		}

	}

}
