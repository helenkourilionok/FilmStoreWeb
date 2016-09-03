package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.entity.Order;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;

public final class AnnulOrderCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(AnnulOrderCommand.class);
	
	private final static String ID = "id";

	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if((session == null)||(session.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}

		String orderId = request.getParameter(ID);
		Order order = null;
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		OrderService orderService = filmStoreServiceFactory.getOrderService();
		
		try {
			order = orderService.find(orderId);
			orderService.update(order.getUserEmail(),order.getCommonPrice().toString(),order.getStatus().name(),order.getKindOfDelivery().name(), 
					order.getKindOfPayment().name(),
					order.getDateOfDelivery().toString(),
					order.getDateOfOrder().toString(),order.getAddress());
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectOrderParamException e) {
			logger.error("Incorrect order id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch (FilmStoreServiceInvalidOrderOperException e) {
			logger.error("Invalid operation!Can't update order!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

}
