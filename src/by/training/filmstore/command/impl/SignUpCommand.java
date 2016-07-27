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
import by.training.filmstore.service.exception.FilmStoreServiceParamIncorrectException;

public class SignUpCommand implements Command {

	private final static Logger logger = LogManager.getLogger(SignUpCommand.class);
	
	private final static String LOCALE = "locale";
	private final static String EMAIL = "email";
	private final static String PASSWORD = "password";
	private final static String COPY_PASSWORD = "copypassword";
	private final static String LAST_NAME = "last_name";
	private final static String FIRST_NAME = "first_name";
	private final static String PATRONIMIC = "patronimic";
	private final static String PHONE = "phone";
	private final static String BALANCE = "balance";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		boolean redirect = Boolean.parseBoolean(request.getParameter(CommandParamName.REDIRECT));

		if (redirect) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_SIGNUP).forward(request, response);;
		} else {
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

			try {
				User user =  userService.create(email, password, copyPassword, lastName,
						firstName, patronimic,phone, balance);
				response.addCookie(new Cookie(CommandParamName.LANGUAGE,request.getParameter(CommandParamName.LANGUAGE)));
				session.setAttribute(LOCALE,request.getParameter(CommandParamName.LANGUAGE));
				session.setAttribute(CommandParamName.USER_EMAIL, user.getEmail());
				session.setAttribute(CommandParamName.USER_ROLE, user.getRole());
				System.out.println("youhoo!"+user.getEmail());
				request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
			} catch (FilmStoreServiceException e) {
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			} catch (FilmStoreServiceParamIncorrectException e) {
				logger.error("Validation failed!",e);
				request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			}
		}

	}

}
