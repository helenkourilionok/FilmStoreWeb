package by.training.filmstore.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.ConvertStringToIntUtil;
import by.training.filmstore.command.util.CookieUtil;

public class PutInBasketCommand implements Command {

	private final static String FILM_ID = "id";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String prev_query = (String) request.getSession(false).getAttribute(CommandParamName.PREV_QUERY);
		
		HttpSession session = request.getSession(true);
		
		String filmId = request.getParameter(FILM_ID);
		String value = CookieUtil.getValueFromCookies(request,CommandParamName.COOKIE_PREFIX_FOR_ORDER+filmId);
		int countFilms = ConvertStringToIntUtil.getIntFromString(value);
		
		countFilms = countFilms == -1?1:countFilms++;
		Cookie cookie = new Cookie(CommandParamName.COOKIE_PREFIX_FOR_ORDER+filmId,String.valueOf(countFilms));
		cookie.setMaxAge(CommandParamName.MAX_AGE_COOKIE);
		response.addCookie(cookie);
		session.setAttribute(CommandParamName.COUNT_FILMS_IN_BASKET, CookieUtil.getCountValuesInCookie(request,CommandParamName.COOKIE_PREFIX_FOR_ORDER));
		response.sendRedirect(prev_query);
	}

}
