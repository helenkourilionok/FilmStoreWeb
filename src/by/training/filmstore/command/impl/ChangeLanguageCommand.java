package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;

public class ChangeLanguageCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		
		session.setAttribute(CommandParamName.LOCALE, request.getParameter(CommandParamName.LANGUAGE));
		String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
		
		Cookie locale = new Cookie(CommandParamName.LOCALE, request.getParameter(CommandParamName.LANGUAGE));
		locale.setMaxAge(CommandParamName.MAX_AGE_COOKIE);
		response.addCookie(locale);
		
		if (prev_query != null) {

			response.sendRedirect(prev_query);
			
		} else {
			
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
			
		}
	}

}
