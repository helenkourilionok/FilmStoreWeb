package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;

public class LogoutCommand implements Command {

//	private final static String LOCALE = "locale";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);
		session.invalidate();
//		String defaultLanguage = "ru";
//		session.setAttribute(LOCALE, defaultLanguage);
//		session.setAttribute(CommandParamName.USER_EMAIL, null);
//		session.setAttribute(CommandParamName.USER_ROLE, Role.ROLE_GUEST);
//		session.setAttribute(CommandParamName.PREV_QUERY, null);
		request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
	}

}
