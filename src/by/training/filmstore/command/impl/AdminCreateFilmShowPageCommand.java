package by.training.filmstore.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.training.filmstore.command.Command;
import by.training.filmstore.command.util.QueryUtil;
import by.training.filmstore.entity.Actor;
import by.training.filmstore.entity.FilmDirector;
import by.training.filmstore.service.ActorService;
import by.training.filmstore.service.FilmDirectorService;
import by.training.filmstore.service.FilmStoreServiceFactory;
import by.training.filmstore.service.exception.FilmStoreServiceException;

public class AdminCreateFilmShowPageCommand implements Command {
	
	private final static String FILM_CREATION_FAILED = "filmCreationFailed";
	
	private final static String FILM = "film";
	//create and update page have similar content with session attribute "film"
	private final static String LIST_ACTORS = "listActors";
	private final static String LIST_FILM_DIR = "listFilmDir";
	private final static String LIST_COUNTRIES = "listCountries";
	private final static String LIST_GENRES = "listGenres";
	private final static String LIST_QUALITY = "listQuality";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession sessionCheckRole = request.getSession(false);
		if ((sessionCheckRole == null)||(!sessionCheckRole.getAttribute(CommandParamName.USER_ROLE).toString().equals("ROLE_ADMIN"))) {
			request.getRequestDispatcher(CommandParamName.PATH_ACESS_DENIED_PAGE).forward(request, response);
			return;
		}
		
		
		String query = QueryUtil.createHttpQueryString(request);
		sessionCheckRole.setAttribute(CommandParamName.PREV_QUERY, query);
		
		FilmStoreServiceFactory filmStoreServiceFactory = FilmStoreServiceFactory.getServiceFactory();
		ActorService actorService = filmStoreServiceFactory.getActorService();
		FilmDirectorService filmDirService = filmStoreServiceFactory.getFilmDirectorService();
		
		List<Actor> listActors = null;
		List<FilmDirector> listFilmDir = null;
		
		try {
			checkRequest(request);
		    listActors = actorService.findAllActors();
		    listFilmDir = filmDirService.findAllFilmDirectors();
		    sessionCheckRole.setAttribute(FILM, null);
		    request.setAttribute(LIST_ACTORS, listActors);
		    request.setAttribute(LIST_FILM_DIR, listFilmDir);
		    request.setAttribute(LIST_COUNTRIES, CommandParamName.listCountries);
		    request.setAttribute(LIST_GENRES,CommandParamName.listGenres);
		    request.setAttribute(LIST_QUALITY, CommandParamName.listQuality);
			request.getRequestDispatcher(CommandParamName.PATH_CREATE_FILM_PAGE).forward(request, response);
		} catch (FilmStoreServiceException e) {
			request.getRequestDispatcher(CommandParamName.PATH_ERROR_PAGE).forward(request, response);
		} 
		
	}
	
	private void checkRequest(HttpServletRequest request){
		String creationFailed = request.getParameter(FILM_CREATION_FAILED);
		request.setAttribute(FILM_CREATION_FAILED, creationFailed);
	}
}
