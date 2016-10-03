package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public final class UpdateUserCommand implements Command {

	private final static Logger logger = LogManager.getLogger(UpdateUserCommand.class);
	
	private final static String LAST_NAME = "last_name";
	private final static String FIRST_NAME = "first_name";
	private final static String PATRONYMIC = "patronymic";
	private final static String PHONE = "phone";
	private final static String BALANCE = "balance";
	private final static String USER = "user";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(false);
		if(CheckUserRoleUtil.isGuest(session)){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}
		
		String userEmail = (String)session.getAttribute(CommandParamName.USER_EMAIL);
		String lastName = request.getParameter(LAST_NAME);
		String firstName = request.getParameter(FIRST_NAME);
		String patronymic = request.getParameter(PATRONYMIC);
		String phone = request.getParameter(PHONE);
		String balance = request.getParameter(BALANCE);
		User user = null;
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactory.getUserService();
		
		try {
			user = userService.find(userEmail);
			phone = formatPhone(phone);
			user = userService.update(user.getEmail(),user.getPassword(),user.getPassword(), 
					lastName, firstName, patronymic,phone, balance);
			
			session.setAttribute(CommandParamName.BALANCE,user.getBalance());
			request.setAttribute(USER, user);
			request.getRequestDispatcher(CommandParamName.PATH_PERSONAL_INFO).forward(request, response);;
		} catch (FilmStoreServiceException e) {
			logger.error("Operation failed!Can't update user!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidUserOperException e) {
			logger.error("Operation failed!Can't update user!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch(FilmStoreServiceIncorrectUserParamException e){
			logger.error("Incorrect user params!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}

	private String formatPhone(String phone){
		String phonePattern = "^\\+([0-9]{3})([0-9]{2})([0-9]{3})([0-9]{2})([0-9]{2})$";
		Pattern patternPhone = Pattern.compile(phonePattern);
		Matcher matcherPhone = patternPhone.matcher(phone);
		String delimiter = "-";
		String formatPhone = "+";
		if (matcherPhone.find()) {
			formatPhone = formatPhone+matcherPhone.group(1)+delimiter+matcherPhone.group(2)+delimiter+
			matcherPhone.group(3)+delimiter+matcherPhone.group(4)+delimiter+matcherPhone.group(5);
		}
		return formatPhone;
	}
}
