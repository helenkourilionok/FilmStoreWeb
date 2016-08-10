package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectParamException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public class ShowListFilmCommand implements Command {

	private final static Logger logger = LogManager.getLogger(ShowListFilmCommand.class);

	private final static String COUNT_PAGES = "countPages";
	private final static String CURRENT_PAGE = "currentPage";
	private final static String LIST_FILM_ATTR = "listFilm";
	private final static String NOT_FOUND_ATTR = "notFoundFilmForRequest";
	private final static String NUMBER_PAGE = "page";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);

		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactoryImpl.getFilmService();

		List<Film> listFilm = null;
		boolean lazyInit = false;
		List<Integer> countAllRec = new ArrayList<>();
		int countAllRecords = 0;
		int countPages = 0;
		
		try {
			int page = 1;
	        if(request.getParameter(NUMBER_PAGE) != null)
	            page = Integer.parseInt(request.getParameter(NUMBER_PAGE));
	        listFilm = filmService.findAllFilms(lazyInit,(page-1)*CommandParamName.FILM_RECORDS_PER_PAGE,
	     													CommandParamName.FILM_RECORDS_PER_PAGE,
	     													countAllRec);
			countAllRecords = countAllRec.get(0);
			countPages = (int) Math.ceil(countAllRecords * 1.0 / CommandParamName.FILM_RECORDS_PER_PAGE);
			
			request.setAttribute(LIST_FILM_ATTR, listFilm);
			request.setAttribute(COUNT_PAGES, countPages);
			request.setAttribute(CURRENT_PAGE, page);
	        
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
			
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE);
		} catch (FilmStoreServiceListFilmNotFoundException e){
			logger.error("None film wasn't found!",e);
			request.setAttribute(NOT_FOUND_ATTR,"true");
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX).forward(request, response);
		} catch (FilmStoreServiceIncorrectParamException e) {
			logger.error("Incorrect parametrs!",e);
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_INDEX_REDIRECT).forward(request, response);
		}

	}
}
