package by.training.filmstore.command.impl;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public final class AdminMakeDiscountShowUserCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminMakeDiscountShowUserCommand.class);
	
	private final static String CURRENT_DATE = "currentDate";
	private final static String DATE = "date";
	private final static String COUNT_ORDERS = "countOrders";
	private final static String LIST_USER_FOR_DISCOUNT = "listUserForDiscount";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||
		(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactory.getUserService();
		
		String date = request.getParameter(DATE);
		LocalDate discountDate = parseDate(date);
		String countOrders = request.getParameter(COUNT_ORDERS);
		List<User> listUserForDiscount = null;

		try {
		
			listUserForDiscount = findUserForDiscount(discountDate, countOrders, userService);
			
			request.setAttribute(LIST_USER_FOR_DISCOUNT, listUserForDiscount);
			request.setAttribute(COUNT_ORDERS, countOrders);
			request.setAttribute(CURRENT_DATE,Date.valueOf(discountDate));
			request.getRequestDispatcher(CommandParamName.PATH_MAKE_DISCOUNT_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectUserParamException e) {
			logger.error("Incorrect params!Can't make discount!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidUserOperException e) {
			logger.error("Operation failed!Can't make discount!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}

	}
	
	private LocalDate parseDate(String date){
		String pattern = "dd/MM/uuuu";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	    return LocalDate.parse(date, dateTimeFormatter);
	}
	
	private List<User> findUserForDiscount(LocalDate discountDate,
						String countOrders,UserService userService) throws FilmStoreServiceException, FilmStoreServiceIncorrectUserParamException, FilmStoreServiceInvalidUserOperException{
		List<User> listUser = new ArrayList<>();
		String year = String.valueOf(discountDate.getYear());
		String month = String.valueOf(discountDate.getMonthValue());
		listUser = userService.findUserForMakeDiscount(year, month, countOrders);
		return listUser;	
	}
}
