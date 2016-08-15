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
import by.training.filmstore.command.util.ConvertStringToIntUtil;
import by.training.filmstore.command.util.PageUtil;
import by.training.filmstore.command.util.PaginationUtil;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Film;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;
import by.training.filmstore.service.exception.FilmStoreServiceListFilmNotFoundException;

public class AdminShowListFilmCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminShowListFilmCommand.class);
		
	private final static int COUNT_REF_PER_PAGE = 5;
	private final static int FILM_RECORDS_PER_PAGE = 5;

	private final static String PAGE_INFO = "pageInfo";
	private final static String LIST_FILM_ATTR = "listFilm";
	private final static String NOT_FOUND_ATTR = "notFoundFilmForRequest";
	private final static String NUMBER_PAGE = "page";
	private final static String START_INDEX = "start";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);

		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)
				|| (!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		FilmStoreServiceFactory filmStoreServiceFactoryImpl = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactoryImpl.getFilmService();

		List<Film> listFilm = null;
		boolean lazyInit = true;
		List<Integer> countAllRec = new ArrayList<>();
		List<Integer> listIndex = null;
		int countAllRecords = 0;
		int countPages = 0;
		PageUtil pageInfo = null;

		try {
			int page = ConvertStringToIntUtil.getIntFromString(request, NUMBER_PAGE);
			int startIndex = ConvertStringToIntUtil.getIntFromString(request, START_INDEX);
			page = page==-1?1:page;
			startIndex = startIndex==-1?1:startIndex;
			
			listFilm = filmService.findAllFilms(lazyInit, 
					(page - 1) *FILM_RECORDS_PER_PAGE,
					FILM_RECORDS_PER_PAGE, countAllRec);
			
			countAllRecords = countAllRec.get(0);
			
			countPages = (int) Math.ceil(countAllRecords * 1.0 / FILM_RECORDS_PER_PAGE);
			
			listIndex = PaginationUtil.makeListIndex(page, startIndex, countPages,COUNT_REF_PER_PAGE);
			pageInfo = new PageUtil(page, countPages,listIndex);
			
			request.setAttribute(LIST_FILM_ATTR, listFilm);
			request.setAttribute(PAGE_INFO, pageInfo);

			request.getRequestDispatcher(CommandParamName.PATH_LIST_FILM_PAGE).forward(request, response);

		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE);
		} catch (FilmStoreServiceListFilmNotFoundException e) {
			logger.error("None film wasn't found!", e);
			request.setAttribute(NOT_FOUND_ATTR, "true");
			request.getRequestDispatcher(CommandParamName.PATH_LIST_FILM_PAGE).forward(request, response);
		} catch (FilmStoreServiceIncorrectFilmParamException e) {
			logger.error("Incorrect parametrs!", e);
			request.getRequestDispatcher(CommandParamName.PATH_LIST_FILM_PAGE).forward(request, response);
		}
	}

}
