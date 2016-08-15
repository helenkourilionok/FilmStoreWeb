package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.Film;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.FilmService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;
import by.training.filmstore.service.exception.FilmStoreServiceFilmNotFoundException;
import by.training.filmstore.service.exception.FilmStoreServiceIncorrectFilmParamException;

public class AdminUpdateFilmShowPageCommand implements Command {

	private final static Logger logger = LogManager.getLogger(AdminUpdateFilmShowPageCommand.class);
	
	private final static String FILM_ID = "id";
	private final static String FILM = "film";
	private final static String LIST_ACTORS = "listActors";
	private final static String LIST_FILM_DIR = "listFilmDir";
	private final static String LIST_COUNTRIES = "listCountries";
	private final static String LIST_GENRES = "listGenres";
	private final static String LIST_QUALITY = "listQuality";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)
				|| (!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_PAGE_LOGIN).forward(request, response);
		}
		
		HttpSession session = request.getSession(true);
		String query = QueryUtil.createHttpQueryString(request);
		session.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		FilmService filmService = filmStoreServiceFactory.getFilmService();
		ActorService actorService = filmStoreServiceFactory.getActorService();
		FilmDirectorService filmDirService = filmStoreServiceFactory.getFilmDirectorService();

		List<Actor> listActors = null;
		List<FilmDirector> listFilmDir = null;
		Film film = null;
		boolean lazyInit = false;
		String filmId = request.getParameter(FILM_ID);
		
		try {
		    listActors = actorService.findAllActors();
		    listFilmDir = filmDirService.findAllFilmDirectors();
		    film = filmService.find(filmId, lazyInit);
		    request.setAttribute(LIST_ACTORS, listActors);
		    request.setAttribute(LIST_FILM_DIR, listFilmDir);
		    request.setAttribute(LIST_COUNTRIES, CommandParamName.listCountries);
		    request.setAttribute(LIST_GENRES,CommandParamName.listGenres);
		    request.setAttribute(LIST_QUALITY, CommandParamName.listQuality);
		    session.setAttribute(FILM, film);
			request.getRequestDispatcher(CommandParamName.PATH_UPDATE_FILM_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch(FilmStoreServiceFilmNotFoundException e){
			logger.error("Film wasn't found!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} catch(FilmStoreServiceIncorrectFilmParamException e){
			logger.error("Incorrect film id!",e);
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		}
	}

}
