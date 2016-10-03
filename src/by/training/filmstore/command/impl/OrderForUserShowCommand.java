package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;

public class OrderForUserShowCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(OrderForUserShowCommand.class);
	
	private final static String LIST_ORDER = "listOrder";
	private final static String STATUS = "status";
	private final static String NOT_ENOUGH_MONEY = "notEnoughMoney";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession sessionCheckRole = request.getSession(false);
		if(CheckUserRoleUtil.isGuest(sessionCheckRole)){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		OrderService orderService = filmStoreServiceFactory.getOrderService();
		
		String userEmail = (String)sessionCheckRole.getAttribute(CommandParamName.USER_EMAIL);
		String status = request.getParameter(STATUS);
		String notEnoughMoney = request.getParameter(NOT_ENOUGH_MONEY);
		List<Order> listUserOrder = null;
		
		try {
			listUserOrder = orderService.findOrderByUserEmailAndStatus(userEmail,Status.valueOf(status).getNameStatus());

			request.setAttribute(LIST_ORDER, listUserOrder);
			request.setAttribute(STATUS,Status.valueOf(status));
			request.setAttribute(NOT_ENOUGH_MONEY, notEnoughMoney);
			request.getRequestDispatcher(CommandParamName.PATH_PERSONAL_INFO).forward(request, response);
		} catch (FilmStoreServiceException e) {
			logger.error("Can't find orders for this user!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectOrderParamException e) {
			logger.error("Incorrect user email!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}
}
