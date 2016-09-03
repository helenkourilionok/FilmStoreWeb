package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.CookieUtil;

public final class RemoveFilmFromBasketCommand implements Command {
	
	private final static String FILM_ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)
				|| (sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_GUEST"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
			return;
		}
		
		String filmId = request.getParameter(FILM_ID);

		CookieUtil.removeOrderCookies(request, response, CommandParamName.COOKIE_PREFIX_FOR_ORDER, filmId);
		
		String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
		
		if (prev_query != null) {

			response.sendRedirect(prev_query);
			
		} else {
			
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
			
		}
	}

}
