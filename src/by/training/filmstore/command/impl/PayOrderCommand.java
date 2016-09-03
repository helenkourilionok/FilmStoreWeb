package by.training.filmstore.command.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.Order;
import by.training.filmstore.entity.Status;
import by.training.filmstore.entity.User;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.UserService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectUserParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidUserOperException;

public final class PayOrderCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(PayOrderCommand.class);
	
	private final static String LIST_ORDER = "listOrder";
	private final static String STATUS = "status";
	private final static String NOT_ENOUGH_MONEY = "notEnoughMoney";
	private final static String ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if((session == null)||(session.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}

		String userEmail = (String)session.getAttribute(CommandParamName.USER_EMAIL);
		String orderID = request.getParameter(ID);
		User user = null;
		Order order = null;

		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		UserService userService = filmStoreServiceFactory.getUserService();
		OrderService orderService = filmStoreServiceFactory.getOrderService();
		
		try {
			user = userService.find(userEmail);
			order = orderService.find(orderID);
			
			if(user.getBalance().compareTo(order.getCommonPrice())==-1){
				List<Order> listUserOrder = orderService.findOrderByUserEmailAndStatus(userEmail,Status.PAID.getNameStatus());
				request.setAttribute(LIST_ORDER, listUserOrder);
				request.setAttribute(STATUS,Status.PAID);
				request.setAttribute(NOT_ENOUGH_MONEY, "Not enougn money for pay order!");
				request.getRequestDispatcher(CommandParamName.PATH_PERSONAL_INFO).forward(request, response);
				return;
			}
			
			BigDecimal newBalance = user.getBalance().subtract(order.getCommonPrice());
			user.setBalance(newBalance);
			order.setStatus(Status.PAID);
			userService.update(user.getEmail(), user.getPassword(), user.getPassword(), user.getLastName(), 
							user.getFirstName(), user.getPatronymic(), user.getPhone(), user.getBalance().toString());
			orderService.update(order.getUserEmail(), order.getCommonPrice().toString(), order.getStatus().name(),order.getKindOfDelivery().name(),
					order.getKindOfPayment().name(), order.getDateOfDelivery().toString(),order.getDateOfOrder().toString(), order.getAddress());
			request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectUserParamException e) {
			logger.error("Incorrect user params!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectOrderParamException e) {
			logger.error("Incorrect order params!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidUserOperException e) {
			logger.error("Operation failed!Can't update user!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidOrderOperException e) {
			logger.error("Operation failed!Can't update order!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

}
