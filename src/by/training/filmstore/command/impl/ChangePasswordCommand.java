package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public final class ChangePasswordCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(ChangePasswordCommand.class);

	private final static String EMAIL = "email";
	private final static String PASSWORD = "password";
	private final static String COPY_PASSWORD = "copypassword";
	private final static String USER_DOESNT_EXIST = "userDoesntExist";
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String userEmail = request.getParameter(EMAIL);
		String password = request.getParameter(PASSWORD);
		String copyPassword = request.getParameter(COPY_PASSWORD);
		User user = null;
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactory.getUserService();
		
		try {
			user = userService.find(userEmail);
			
			if(user == null){
				request.setAttribute(EMAIL, userEmail);
				request.setAttribute(USER_DOESNT_EXIST, "User with this email doesn't exist!");
				request.getRequestDispatcher(CommandParamName.PATH_CHANGE_PASSWORD).forward(request, response);
				return;
			}
			
			userService.changePassword(userEmail, password,copyPassword);
			
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectUserParamException e) {
			logger.error("Incorrect user email or password!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidUserOperException e) {
			logger.error("Operation failed!Can't change password!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
		
		
	}

}
