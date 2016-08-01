package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class ShowListFilmCommand implements Command {

	private final static String LIST_FILM_ATTR = "listFilm";
	private final static String NOT_FOUND_MESSAGE = "None film was found for request";
	private final static String NOT_FOUND_ATTR = "notFoundFilmForRequest"; 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);

		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactoryImpl.getFilmService();

		try {
			List<Film> listFilm = filmService.findAllFilms();
			if(!listFilm.isEmpty()){
				request.setAttribute(LIST_FILM_ATTR, listFilm);
			}else{
				request.setAttribute(NOT_FOUND_ATTR,NOT_FOUND_MESSAGE);
			}
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE);
		} 

	}
}