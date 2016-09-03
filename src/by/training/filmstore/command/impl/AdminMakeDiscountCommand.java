package by.training.filmstore.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public final class AdminMakeDiscountCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminMakeDiscountCommand.class);
	
	private final static String DATE = "date";
	private final static String SIZE_OF_DISCOUNT = "sizeofDiscount";
	private final static String COUNT_ORDERS = "countOrders";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||
		(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		String date = request.getParameter(DATE);
		String sizeOfDiscount = request.getParameter(SIZE_OF_DISCOUNT);
		String countOrders = request.getParameter(COUNT_ORDERS);
		LocalDate discountDate = parseDate(date);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactory.getUserService();
		
		try {
			makeDiscount(sizeOfDiscount, discountDate, countOrders, userService);
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectUserParamException e) {
			logger.error("Incorrect params!Can't make discount!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidUserOperException e) {
			logger.error("Operation failed!Can't make discount!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}
	
	private LocalDate parseDate(String date){
		String pattern = "dd/MM/uuuu";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
	    return LocalDate.parse(date, dateTimeFormatter);
	}
	
	private void makeDiscount(String sizeOfDiscount,LocalDate discountDate,String countOrders,UserService userService)
			throws FilmStoreServiceIncorrectUserParamException, 
			FilmStoreServiceInvalidUserOperException,
			FilmStoreServiceException{
		String year = String.valueOf(discountDate.getYear());
		String month = String.valueOf(discountDate.getMonthValue());
		userService.makeDiscount(sizeOfDiscount,year, month, countOrders);
	}
}
