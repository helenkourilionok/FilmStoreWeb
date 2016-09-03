package by.training.filmstore.command.impl;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;

public final class AdminMakeDiscountShowPageCommand implements Command {
	
	private final static String CURRENT_DATE = "currentDate";
	private final static String COUNT_ORDERS = "countOrders";
	
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
		
		Date currentDate = Date.valueOf(LocalDate.now());
		int defaultCount = 1;
		request.setAttribute(CURRENT_DATE, currentDate);
		request.setAttribute(COUNT_ORDERS, defaultCount);
		request.getRequestDispatcher(CommandParamName.PATH_MAKE_DISCOUNT_PAGE).forward(request, response);
	}

}
