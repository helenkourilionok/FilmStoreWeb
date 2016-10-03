package by.training.filmstore.command.impl;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CheckUserRoleUtil;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.OrderService;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectOrderParamException;
import by.training.filmstore.service.exception.FilmStoreServiceInvalidOrderOperException;
import by.training.filmstore.service.impl.ValidationParamUtil;

public final class PayOrderCommand implements Command {
	
	private final static Logger logger = LogManager.getLogger(PayOrderCommand.class);
	
	private final static String NOT_ENOUGH_MONEY = "notEnoughMoney";
	private final static String PRICE = "price";
	private final static String ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if(CheckUserRoleUtil.isGuest(session)){
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}

		String userEmail = (String)session.getAttribute(CommandParamName.USER_EMAIL);
		String orderId = request.getParameter(ID);
		String price = request.getParameter(PRICE);
		String prev_query = (String) session.getAttribute(CommandParamName.PREV_QUERY);
		
		BigDecimal commonPrice = ValidationParamUtil.validateBalance(price);
		BigDecimal userBalance = (BigDecimal)session.getAttribute(CommandParamName.BALANCE);

		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		OrderService orderService = filmStoreServiceFactory.getOrderService();
		

			try {
				
				BigDecimal newUserBalance = checkUserBalance(userBalance, commonPrice); 

				if(newUserBalance==null){
					response.sendRedirect(prev_query+"&"+NOT_ENOUGH_MONEY+"=true");
					return;
				}
				
				orderService.payOrder(newUserBalance, orderId, userEmail);
				
				session.setAttribute(CommandParamName.BALANCE, newUserBalance);
				
				request.getRequestDispatcher(CommandParamName.PATH_SUCCESS_PAGE).forward(request, response);
			} catch (FilmStoreServiceException e) {
				logger.error("Operation failed!Can't pay order!",e);
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			} catch (FilmStoreServiceIncorrectOrderParamException e) {
				logger.error("Incorrect order params!Can't pay order!",e);
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			} catch (FilmStoreServiceInvalidOrderOperException e) {
				logger.error("Operation failed!Can't pay order!",e);
				request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
			}
	}	
	
	private BigDecimal checkUserBalance(BigDecimal userBalance,BigDecimal commonPrice){
		
		BigDecimal newUserBalance = userBalance.subtract(commonPrice);
		if(newUserBalance.compareTo(BigDecimal.ZERO)<0){
			newUserBalance = null;
		}
		
		return newUserBalance;
	}
}
