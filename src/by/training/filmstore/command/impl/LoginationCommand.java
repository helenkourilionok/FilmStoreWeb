package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CookieUtil;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceAuthException;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class LoginationCommand implements Command {

	private final static Logger logger = LogManager.getLogger(LoginationCommand.class);
	private final static String EMAIL = "email";
	private final static String PASSWORD = "password";
	private final static String ERROR_ATTRIBUTE = "incorrectEmailOrPassword";
	private final static String DEFAULT_LANGUAGE = "ru";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		String email = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);

		try {

			userAuth(email, password, session);
			String language = CookieUtil.getValueFromCookies(request, CommandParamName.LANGUAGE);
			language = language == null ? DEFAULT_LANGUAGE : language;
			session.setAttribute(CommandParamName.LOCALE, language);

			String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
			if (prev_query != null) {

				response.sendRedirect(prev_query);

			} else {

				request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);

			}
		} catch (FilmStoreServiceException e) {

			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);

		} catch (FilmStoreServiceAuthException e) {
			logger.error("Authorization failed!User with these email and password wasn't find!",e);
			request.setAttribute(ERROR_ATTRIBUTE,"true");
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);

		}

	}

	private void userAuth(String email, String password, HttpSession httpSession)
			throws FilmStoreServiceException, FilmStoreServiceAuthException {

		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactoryImpl.getUserService();

		User user = userService.authorisation(email, password);
		
		httpSession.setAttribute(CommandParamName.DISCOUNT, user.getDiscount());
		httpSession.setAttribute(CommandParamName.USER_EMAIL, user.getEmail());
		httpSession.setAttribute(CommandParamName.USER_ROLE, user.getRole().name());

	}
}
